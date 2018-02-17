//
//  main.cpp
//  Zad2
//
//  Created by Filip Gulan on 14/04/16.
//  Copyright © 2016 Filip Gulan. All rights reserved.
//

#include <iostream>
#include <assert.h>

struct Point{
    int x;
    int y;
};

struct Shape {
    enum EType {circle, square, rhomb};
    EType type_;
};

struct Circle {
    Shape::EType type_;
    double radius_;
    Point center_;
};

struct Square {
    Shape::EType type_;
    double side_;
    Point center_;
};

struct Rhomb {
    Shape::EType type_;
    double side_;
    Point center_;
    double angle;
};

void drawSquare(struct Square*) {
    std::cerr <<"in drawSquare\n";
}

void drawCircle(struct Circle*) {
    std::cerr <<"in drawCircle\n";
}

void drawRhomb(struct Rhomb*) {
    std::cerr <<"in drawRhomb\n";
}

void drawShapes(Shape** shapes, int n){
    for (int i=0; i<n; ++i){
        struct Shape* s = shapes[i];
        switch (s->type_){
            case Shape::square:
                drawSquare((struct Square*)s);
                break;
            case Shape::circle:
                drawCircle((struct Circle*)s);
                break;
            case Shape::rhomb:
                drawRhomb((struct Rhomb*)s);
                break;
            default:
                assert(0);
                exit(0);
        }
    }
}

void moveSquare(struct Square *square, int dx, int dy) {
    square->center_.x += dx;
    square->center_.y += dy;
    std::cout << "in moveSquare\n";
}

void moveCircle(struct Circle *circle, int dx, int dy) {
    circle->center_.x += dx;
    circle->center_.y += dy;
    std::cout << "in moveCircle\n";
}

void moveRhomb(struct Rhomb *rhomb, int dx, int dy) {
    rhomb->center_.x += dx;
    rhomb->center_.y += dy;
    std::cout << "in moveRhomb\n";
}

void moveShapes(Shape** shapes, int n, int dx, int dy) {
    for (int i=0; i<n; ++i){
        struct Shape* s = shapes[i];
        switch (s->type_){
            case Shape::square:
                moveSquare((struct Square*)s, dx, dy);
                break;
            case Shape::circle:
                moveCircle((struct Circle*)s, dx, dy);
                break;
            case Shape::rhomb:
                moveRhomb((struct Rhomb*)s, dx, dy);
                break;
            default:
                assert(0);
                exit(0);
        }
    }
}

int main(){
    Shape* shapes[5];
    shapes[0]=(Shape*)new Circle;
    shapes[0]->type_=Shape::circle;
    shapes[1]=(Shape*)new Square;
    shapes[1]->type_=Shape::square;
    shapes[2]=(Shape*)new Square;
    shapes[2]->type_=Shape::square;
    shapes[3]=(Shape*)new Circle;
    shapes[3]->type_=Shape::circle;
    shapes[4]=(Shape*)new Rhomb;
    shapes[4]->type_=Shape::rhomb;
    drawShapes(shapes, 5);
    moveShapes(shapes, 5, 1, 2);
}
