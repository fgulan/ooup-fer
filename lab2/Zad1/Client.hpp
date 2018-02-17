//
//  Client.hpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#ifndef Client_hpp
#define Client_hpp

#include "Base.hpp"

class Client {
private:
    Base& solver_;
public:
    Client(Base& b): solver_(b){}
    void operate();
};

#endif /* Client_hpp */
