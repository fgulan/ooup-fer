//
//  Client1.hpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 17/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#ifndef Client1_hpp
#define Client1_hpp

#include "MockDatabase.hpp"

class Client1 {
    MockDatabase myDatabase;
public:
//    Client1() : myDatabase() {}
    void transaction() {
        myDatabase.getData();
    }
};

#endif /* Client1_hpp */
