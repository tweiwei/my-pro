#include "stdio.h"

int addtest(int a, int b, int c,int d, int e, int f, int g, int h, int i)
{
    return a + b + c + d+ e + f + g + h + i;
}
void C_function()
{
    int s = 0;
    s = addtest(1,2,3,4,5,6,7,8,9);
    printf("This is C_function! s = %d\n", s);
    return;
}