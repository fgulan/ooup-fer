//
//  Client.cpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#include "Client.hpp"
#include <iostream>

void Client::operate()
{
    std::cout << solver_.solve() << "\n";
}
