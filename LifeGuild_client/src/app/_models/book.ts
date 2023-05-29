// https://openlibrary.org/works/OL18020194W/bookshelves.json

//cover images are in format
//https://covers.openlibrary.org/b/id/{{covers[0]}}-M.jpg
//eg https://covers.openlibrary.org/b/id/6498519-M.jpg
//M is medium sized image, there's L and S for large and small as well.
//data comes from several apis from https://openlibrary.org/dev/docs/api/books

//switched to google books, ignore above.
export interface Book {
  title: string;
  externalUrl: string;
  imgUrl: string;
  description: string;
  // covers: number[]; //cover images
  // subjects: string[];
  // wantToRead: number;
  // currentlyReading: number;
  // alreadyRead: number;
  // averageRating: number;
  // linkToEdition: string;
}

