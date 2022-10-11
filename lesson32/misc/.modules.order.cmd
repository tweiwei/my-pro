cmd_/home/lmos/计算机基础/code/30/misc/modules.order := {   echo /home/lmos/计算机基础/code/30/misc/miscdrv.ko; :; } | awk '!x[$$0]++' - > /home/lmos/计算机基础/code/30/misc/modules.order
