#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int run(char* cmd)
{
	pid_t pid;
	int rets;
	//建立子进程
	pid = fork();
	if(pid > 0)
	{   //等待子进程退出 
		wait(&rets);
	}
	else if(pid == 0)
	{	//新进程加载新应用
		if(execl(cmd, cmd, NULL) == -1)
		{
			printf("未找到该应用\n");
			exit(0);
		}
	}
	return 0;
}

int shell_run()
{
	char instr[80];
	while(1)
	{
		printf("请输入应用名称：");
		//获取用户输入
		scanf("%[^\n]%*c", instr);
		//判断是exit就退出
		if(strncmp("exit", instr, 4) == 0)
		{
			return 0;
		}
		//执行命令
		run(instr);
	}
	return 0;	
}

int main()
{
	return shell_run();
}