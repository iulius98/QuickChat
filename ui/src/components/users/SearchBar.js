import React from "react";

import { InputAdornment, OutlinedInput, Typography } from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';

export default function SearchBar(props) {

    const handleSeacrhChange = (event) =>{
        props.handleSeacrh(event.target.value);
    }

    return (
        <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
            <Typography> Search: </Typography>
            <OutlinedInput
                margin="dense"
                id="filter-input-with-icon-adornment"
                type="text"
                sx={{width: "75%", marginTop: "2%", marginLeft: "2%", borderRadius: "50px"}}
                value={props.filterText}
                variant="outlined"
                onChange={handleSeacrhChange}
                endAdornment = {
                    <InputAdornment position="end">
                        <SearchIcon fontSize="medium"/>
                    </InputAdornment>
                } 
            />
        </div>
    );
}