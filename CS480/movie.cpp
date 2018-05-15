#include "movie.h"
#include "rental.h"
#include "renter.h"



Movie::Movie(string inp_title, string mv_type){
  title = inp_title;
  if (mv_type == "regular"||
    mv_type == "children"||
    mv_type == "new release")
      {
        movie_type = mv_type;
      }
  else
    {
      cout << "Invalid movie type, defaulting to regular" << endl;
      movie_type="regular";
    }

}
