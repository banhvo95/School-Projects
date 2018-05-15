#ifndef MOVIE_H
#define MOVIE_H

#include <iostream>
using namespace std;

class Movie{

  private:
    string title;
    string movie_type;

  public:
    Movie(string inp_title, string mv_type);
    string get_title(){return title;}
    string get_type(){return movie_type;}
};


#endif
