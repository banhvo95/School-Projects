#include "movie.h"
#include "rental.h"
#include "renter.h"



void Renter::set_points(int new_points){
  freq_pts=freq_pts + new_points;
  }


int Renter::get_points(){ return freq_pts;}

string Renter::get_name(){ return name;}
