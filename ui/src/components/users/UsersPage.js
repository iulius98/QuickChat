import React from "react";
import SearchBar from "./SearchBar";
import UsersList from "./UsersList";

export default function UsersPage() {
    const [filterText, setFilterText] = React.useState("");

    const handleSeacrh = (text) => {
        setFilterText(text);
    }

    return (
        <div>
            <div>
                <SearchBar handleSeacrh={handleSeacrh} filterText={filterText}/>
            </div>

            <div>
                <hr style={{width: "95%"}} />
            </div>

            <div>
                <UsersList filterText={filterText}/>
            </div>
        </div> 
    );
}