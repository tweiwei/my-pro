#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
int main()
{
	pid_t pid;
	int rets;
	int fd[2];
	char r_buf[1024] = {0};
	char w_buf[1024] = {0};
	// 把字符串格式化写入w_buf数组中
	sprintf(w_buf, "这是父进程 id = %d\n", getpid());
	// 建立管道
	if(pipe(fd) < 0)
	{
		perror("建立管道失败\n");
	}
	// 建立子进程
	pid = fork();
	if(pid > 0)
	{
		// 写入管道
		write(fd[1], w_buf, strlen(w_buf));
		// 等待子进程退出 
		wait(&rets);
	}
	else if(pid == 0)
	{	
		// 新进程
		printf("这是子进程 id = %d\n", getpid());
		// 读取管道
        read(fd[0], r_buf, strlen(w_buf));
        printf("管道输出:%s\n", r_buf);
	}
	return 0;
}