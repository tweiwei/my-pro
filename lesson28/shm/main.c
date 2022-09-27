#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <unistd.h>
#include <errno.h>
static pid_t subid;
static int shmid;

#define SHM_TYPE (041375)
#define SHM_KEY (752364)
#define SHM_BODY_SIZE (4096-8)
#define SHM_STATUS (SHM_BODY_SIZE)
typedef struct SHM
{
	long status;
	char body[SHM_BODY_SIZE];
} shm_t;

int receive_main(int mid)
{
	// 绑定共享内存
	int ok = 0;
	shm_t* addr = shmat(mid, NULL, 0);
	if ((long)addr < 0)
	{
		perror("绑定共享内存失败\n");
	}
	printf("子进程访问共享内存的地址:%p\n", addr);

	while (1)
	{
		if(addr->status == SHM_STATUS)
		{
			for (int i = 0; i < SHM_BODY_SIZE; i++)
			{
				if (addr->body[i] != (char)0xff)
				{
					printf("检查共享数据失败:%x\n", addr->body[i]);
				}
				else
				{
					ok++;
				}
				
			}
			printf("检查共享数据成功:%d\n", ok);

			return 0;
		}
		sleep(2);
	}
	return 0;
}

int send_main(int mid)
{
	// 绑定共享内存
	shm_t* addr = shmat(mid, NULL, 0);
	if ((long)addr < 0)
	{
		perror("绑定共享内存失败\n");
	}
	printf("主进程访问共享内存的地址:%p\n", addr);
	memset(addr, 0xff, sizeof(shm_t));
	// 相当于同步通知子进程数据已经写入
	addr->status = SHM_STATUS;
	// 等待子进程退出
	wait(NULL);
	return 0;
}

int main()
{
	pid_t pid;
	// 建立共享内存
	shmid = shmget(SHM_KEY, sizeof(shm_t), IPC_CREAT | 0666);
	if (shmid < 0)
	{
		perror("建立共享内存出错\n");
	}
	// 建立子进程
	pid = fork();
	if (pid > 0)
	{
		// 主进程
		send_main(shmid);
	}
	else if (pid == 0)
	{
		// 新的子进程
		receive_main(shmid);
	}
	return 0;
}
