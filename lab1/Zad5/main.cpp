//
//  main.cpp
//  Zad5
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#include <iostream>

class Base
{
public:
    Base() {
        metoda();
    }
    
    virtual void virtualnaMetoda() {
        printf("ja sam bazna implementacija!\n");
    }
    virtual void test() {
        printf("jo\n");
    }
    
    void metoda() {
        printf("Metoda kaze: ");
        virtualnaMetoda();
        
    }
};

class Derived: public Base
{
public:
    Derived(): Base() {
        metoda();
        test();
    }

    virtual void virtualnaMetoda() {
        printf("ja sam izvedena implementacija!\n");
    }
};

int main(int argc, const char * argv[])
{
    Derived* pd=new Derived();
    pd->metoda();
    return 0;
}
