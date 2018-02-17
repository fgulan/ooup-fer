//
//  main.cpp
//  Zad3
//
//  Created by Filip Gulan on 27/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#include <iostream>

class CoolClass
{
public:
    virtual void set(int x)
    {
        x_=x;
    };
    virtual int get()
    {
        return x_;
    };
private:
    int x_;
};

class PlainOldClass
{
public:
    void set(int x){x_=x;};
    int get(){return x_;};
private:
    int x_;
};

int main(int argc, const char * argv[])
{
    printf("CoolClas : %ld\nPlainOldClass : %ld\n", sizeof(CoolClass), sizeof(PlainOldClass));
    return 0;
}
