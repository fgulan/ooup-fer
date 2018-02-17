//
//  main.cpp
//  Zad1
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#include <iostream>
#include "Client.hpp"
#include "Derived.hpp"
#include "Derived2.hpp"
#include "Derived3.hpp"

int main(int argc, const char * argv[]) {
    Derived3 d;
    Client c(d);
    c.operate();
    return 0;
}
