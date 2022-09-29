
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

static pid_t subid;

void handle_sigusr1(int signum, siginfo_t *info, void *ucontext)
{
	printf("handle_sigusr1 信号码:%d\n", signum);
	//判断是否有数据
	if (ucontext != NULL)
	{
		//保存发送过来的信息
		printf("传递过来的子进程ID:%d\n", info->si_int);
		printf("发送信号的父进程ID:%d\n", info->si_pid);
		// 接收数据
		printf("对比传递过来的子进程ID:%d == Getpid:%d\n", info->si_value.sival_int, getpid());
	}
	// 退出进程
	exit(0);
	return;
}

int subprocmain()
{
	struct sigaction sig;
	// 设置信号处理函数
	sig.sa_sigaction = handle_sigusr1;
	sig.sa_flags = SA_SIGINFO;
	// 安装信号
	sigaction(SIGUSR1, &sig, NULL);
	// 防止子进程退出
	while (1)
	{
		pause(); // 进程输入睡眠，等待任一信号到来并唤醒进程
	}
	return 0;
}

void handle_timer(int signum, siginfo_t *info, void *ucontext)
{
	printf("handle_timer 信号码:%d\n", signum);
	union sigval value;
	// 发送数据，也可以发送指针
	value.sival_int = subid; // 子进程的id
	// 调用sigqueue，向子进程发出SIGUSR1信号
	sigqueue(value.sival_int, SIGUSR1, value);
	return;
}

int main()
{
	pid_t pid;
	// 建立子进程
	pid = fork();
	if (pid > 0)
	{
		// 记录新建子进程的id
		subid = pid;
		struct sigaction sig;
		// 设置信号处理函数
		sig.sa_sigaction = handle_timer;
		sig.sa_flags = SA_SIGINFO;
		// 安装信号
		sigaction(SIGALRM, &sig, NULL);
		alarm(4); // 4秒后发出SIGALRM信号
		while (1)
		{
			pause(); // 进程输入睡眠，等待任一信号到来并唤醒进程
		}
	}
	else if (pid == 0)
	{
		// 新进程
		subprocmain();
	}
	return 0;
}