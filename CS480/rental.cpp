#include "movie.h"
#include "rental.h"
#include "renter.h"



Rental::Rental(Movie* in_mov, int length){
  movie_rental = in_mov;
  rental_length = length;
  rental_price = calc_rental_price();
  if (movie_rental->get_type() == "new release"){
    new_release = true;
  }
  else{
    new_release = false;
  }




}


float Rental::calc_rental_price(){
  if (movie_rental->get_type() == "new release"){
    daily_price = 5.00;
  }
  else if(movie_rental->get_type() == "children"){
    daily_price = 2.50;
  }
  else if(movie_rental->get_type() == "regular"){
    daily_price = 3.50;
  }
  rental_price = daily_price*rental_length;

}


void Rental::set_length(int days){
  rental_length=days;
  rental_price = calc_rental_price();

}
