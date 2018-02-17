//
//  MockDatabase.hpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 15/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#ifndef MockDatabase_hpp
#define MockDatabase_hpp

#include "AbstractDatabase.hpp"
#include <iostream>

class MockDatabase : public AbstractDatabase {
public:
    virtual void getData() {
        std::cout << "getData()" << std::endl;
    }
};
#endif /* MockDatabase_hpp */
