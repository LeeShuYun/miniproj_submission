//transport from view 1 search review
export interface Review {
  title: string;
  rating: string;
  byline: string;
  headline: string;
  summary: string;
  reviewURL: string;
  image: string;
  commentCount: number;
}

//transport from view 3 post comment
export interface Comment {
  movieName: string;
  name: string;
  rating: string;
  comment: string;
}

