import React from "react";
import SearchBar from "./SearchBar";
import UsersList from "./UsersList";

import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';

export default function UsersPage() {
    const [filterText, setFilterText] = React.useState("");
    const [tab, setTab] = React.useState(0);

    const handleTabChange = (event, newValue) => {
        setTab(newValue);
    };

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
                <Tabs
                    value={tab}
                    onChange={handleTabChange}
                    variant="scrollable"
                    scrollButtons="auto"
                    aria-label="scrollable auto tabs example"
                >
                    <Tab label="Item One" />
                    <Tab label="Item Two" />
                    <Tab label="Item Three" />
                    <Tab label="Item Four" />
                    <Tab label="Item Five" />
                    <Tab label="Item Six" />
                    <Tab label="Item Seven" />
                </Tabs>
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