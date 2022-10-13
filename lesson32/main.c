#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <linux/input.h>

#define KB_DEVICE_FILE "/dev/input/event3"

int main(int argc, char *argv[])
{

	int fd = -1, ret = -1;
	struct input_event in;
	char *kbstatestr[] = {"弹起", "按下"};
	char *kbsyn[] = {"开始", "键盘", "结束"};
	//第一步：打开文件
	fd = open(KB_DEVICE_FILE, O_RDONLY);
	if (fd < 0)
	{
		perror("打开文件失败");
		return -1;
	}
	while (1)
	{
		//第二步：读取一个event事件包
		ret = read(fd, &in, sizeof(struct input_event));
		if (ret != sizeof(struct input_event))
		{
			perror("读取文件失败");
			break;
		}
		//第三步：解析event包
		if (in.type == 1)
		{
			printf("------------------------------------\n");
			printf("状态:%s 类型:%s 码:%d 时间:%ld\n", kbstatestr[in.value], kbsyn[in.type], in.code, in.time.tv_usec);
			if (in.code == 46)
			{
				break;
			}
		}
	}
	//第四步：关闭文件
	close(fd);
	return 0;
}