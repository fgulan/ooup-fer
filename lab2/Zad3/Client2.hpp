//
//  Client2.hpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 17/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#ifndef Client2_hpp
#define Client2_hpp

#include "AbstractDatabase.hpp"

class Client2 {
    AbstractDatabase &myDatabase;
public:
    Client2(AbstractDatabase &db) : myDatabase(db) { }
    
    void transaction() {
        myDatabase.getData();
    }
};

#endif /* Client2_hpp */
