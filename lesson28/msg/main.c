#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include <errno.h>
static pid_t subid;
static int msgid;

#define MSG_TYPE (041375)
#define MSG_KEY (752364)
#define MSG_SIZE (256)
typedef struct Msg
{
	long type;
	char body[MSG_SIZE];
} msg_t;

int receive_main(int mid)
{
	msg_t msg;
	while (1)
	{
		ssize_t sz = msgrcv(mid, &msg, MSG_SIZE, MSG_TYPE, MSG_NOERROR);
		if (sz < 0)
		{
			perror("获取消息失败");
		}
		printf("新消息:%s\n", msg.body);
		//判断是exit就退出
		if (strncmp("exit", msg.body, 4) == 0)
		{
			printf("结束聊天\n");
			exit(0);
		}
	}
	return 0;
}

int send_main(int mid)
{
	msg_t msg;

	while (1)
	{
		// 设置消息类型
		msg.type = MSG_TYPE;
        // 获取用户输入并放在消息体中
		scanf("%[^\n]%*c", msg.body);
		// 发送消息
		msgsnd(mid, &msg, MSG_SIZE, 0);
		// 判断是exit就退出
		if (strncmp("exit", msg.body, 4) == 0)
		{
			return 0;
		}
	}
	return 0;
}

int main()
{
	pid_t pid;
	msgid = msgget(MSG_KEY, IPC_CREAT | 0666);
	if (msgid < 0)
	{
		perror("建立消息队列出错\n");
	}
	// 建立子进程
	pid = fork();
	if (pid > 0)
	{
		// 记录新建子进程的id
		subid = pid;
		send_main(msgid);
	}
	else if (pid == 0)
	{
		// 新的子进程
		receive_main(msgid);
	}
	return 0;
}
