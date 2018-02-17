//
//  main.cpp
//  Zad6
//
//  Created by Filip Gulan on 31/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#include <iostream>

typedef int (*PTRFUN)();

class B
{
public:
    virtual int prva() = 0;
    virtual int druga() = 0;
};

class D: public B
{
public:
    virtual int prva()
    {
        return 0;
    }
    virtual int druga()
    {
        return 42;
    }
};


int main(int argc, const char * argv[])
{
    D *obj = new D();
    size_t *vTable = *(size_t**)obj;
    int prva = ((PTRFUN) vTable[0])();
    int druga = ((PTRFUN) vTable[1])();
    printf("Prva: %d\nDruga: %d\n", prva, druga);
}
