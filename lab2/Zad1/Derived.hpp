//
//  Derived.hpp
//  OOuP - Lab 2
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#ifndef Derived_hpp
#define Derived_hpp

#include "Base.hpp"

class Derived : public Base {

public:
    virtual int solve()
    {
        return 42;
    };
};

#endif /* Derived_hpp */
