//
//  main.cpp
//  Zad3
//
//  Created by Filip Gulan on 15/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#include <iostream>
#include "Client2.hpp"
#include "Client1.hpp"


int main(int argc, const char * argv[]) {
    MockDatabase *pdb = new MockDatabase();
    Client2 client(*pdb);
    client.transaction();
    
    Client1 client1;
    client1.transaction();
    
    return 0;
}
