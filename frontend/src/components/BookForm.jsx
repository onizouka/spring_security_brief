import {useState} from "react";
import {createBook} from "../services/bookService.js";


export function BookForm(){
    const [title, setTitle] = useState("");
    const [author, setAuthor] = useState("");
    const [category, setCategory] = useState("");
    const [yearOfPublication, setYearOfPublication] = useState("");
    const [numberOfCopiesAvailable, setNumberOfCopiesAvailable] = useState("");

   async function handleSubmit(event) {

        event.preventDefault();
        try {
            const newBook = {
                title,
                author,
                category,
                yearOfPublication: Number(yearOfPublication),
                numberOfCopiesAvailable: Number(numberOfCopiesAvailable),
            };
            const data = await createBook(newBook);
            console.log(data);
        }catch(error){
            console.error("Erreur lors de l'ajout du livres",error);
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <h2>Ajouter un livre</h2>
            <label htmlFor={"title"}>
                <span>Titre</span>
            <input
                id="title"
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                />
            </label>
            <label htmlFor={"author"}>
                <span>Author:</span>
                <input
                id="author"
                type="text"
                value={author}
                onChange={(e) => setAuthor(e.target.value)}
                />
            </label>
            <label htmlFor={"category"}>
                <span>Category:</span>
                <input
                id="category"
                type="text"
                value={category}
                onChange={(e) => setCategory(e.target.value)}
                />
            </label>
            <label htmlFor={"yearOfPublication"}>
                <span>Year of Publication:</span>
                <input
                id="yearOfPublication"
                type="number"
                value={yearOfPublication}
                onChange={(e) => setYearOfPublication(e.target.value)}
                />
            </label>
            <label htmlFor={"numberOfCopiesAvailable"}>
                <span>Number of Copies:</span>
                <input
                id="numberOfCopiesAvailable"
                type="number"
                value={numberOfCopiesAvailable}
                onChange={(e) => setNumberOfCopiesAvailable(e.target.value)}
                />
            </label>
            <button type="submit">Ajouter</button>
        </form>
    )
}