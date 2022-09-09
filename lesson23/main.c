
void sw_ins(unsigned int addr, int val);//声明sw_ins函数

unsigned int word = 0xffffffff;
int main()
{
    sw_ins((unsigned int)&word, 0);
    return 0;
}