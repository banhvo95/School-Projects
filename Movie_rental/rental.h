#ifndef RENTAL_H
#define RENTAL_H

#include "movie.h"
class Rental{

  private:
    int rental_length;
    bool new_release;
    float rental_price;
    Movie* movie_rental;
    float daily_price;
  public:
    Rental(Movie* in_mov, int length);
    float calc_rental_price();
    void set_length(int days);
    float get_price(){return rental_price;};
    float get_length(){return rental_length;};
    bool get_release(){return new_release;};
    string get_movie_name(){return movie_rental->get_title();}
    string get_movie_type(){return movie_rental->get_type();}
    float get_daily_price(){return daily_price;}
};

#endif
