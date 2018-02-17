//
//  main.cpp
//  Zad4
//
//  Created by Filip Gulan on 27/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#include <iostream>

class Base
{
public:
    //if in doubt, google "pure virtual"
    virtual void set(int x) = 0;
    virtual int get() = 0;
};

class CoolClass: public Base
{
public:
    virtual void set(int x) { x_ = x; };
    virtual int get() { return x_; };
    virtual int get2() { return x_; };

private:
    int x_;
};

class PlainOldClass
{
public:
    void set(int x) { x_=x; };
    int get() { return x_; };
private:
    int x_;
};

int main(int argc, const char * argv[])
{
    PlainOldClass poc;
    Base *pb = new CoolClass;
    poc.set(42);
    pb->set(42);
    return 0;
}
