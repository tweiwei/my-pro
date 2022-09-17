#include "stdio.h"
#include "stdlib.h"
void func_a()
{
    int* p = (int*)0x40000000;
    printf("内存地址:%p\n", p);
    *p = 0xABABBABA;
    printf("内存地址:%p处的值:%x\n", p, *p);
    return;    
}
void func_b()
{
    int* p = (int*)malloc(sizeof(int));
    if(p)
    {
        printf("内存地址:%p\n", p);
        *p = 0xABABBABA;
        printf("内存地址:%p处的值:%x\n", p, *p);
    }
    return;    
}

void mymemset(void* start, char val, int size)
{
    char* buf = (char*)start;
    for(int i = 0; i < size; i++)
    {
        buf[i] = val;
    }
    return;
}
int main()
{
    func_b();
    return 0;
}