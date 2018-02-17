gcc main.c myfactory.c -ldl
gcc -shared tiger.c -o tiger.so
gcc -shared parrot.c -o parrot.so
./a.out