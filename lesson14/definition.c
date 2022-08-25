//定义整形变量
int i = 5;

//也可以如下,这是未初始化的 
//int i;

//定义结构体
struct data
{
    int a;
    char c;
};

//定义结构体变量
// struct data
// {
//     int a;
//     char c;
// }d;

//定义结构体变量并初始化
struct data d = {10, 'a'};

//定义函数
int initdata(struct data* init)
{
    init->a = 20;
    init->c = 'B';
    return 0;
}
