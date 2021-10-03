import React from "react";
import { makeStyles } from "@mui/styles";
import MyAppBar from "./AppBar";

const layoutStyles = makeStyles((theme) => {
  return {
    root: {
      display: "flex",
      maxHeight: "100%",
    },
    app: {
      background: "#f9f9f9",
      width: "100%",
      height: `calc(100% - ${theme.mixins.toolbar.minHeight}px)`,
      maxHeight: `calc(100% - ${theme.mixins.toolbar.minHeight}px)`,
    },
    active: {
      background: "#f4f4f4",
    },
    title: {
      padding: theme.spacing(2),
    },
    toolbar: theme.mixins.toolbar,
  };
});

export default function Layout({ children }) {
  const classes = layoutStyles();
  //   const history = useHistory();
  //   const location = useLocation();

  return (
    <div className={classes.root}>
      {/* app bar */}
      <MyAppBar className={classes.appBar} />

      {/* main content */}
      <div className={classes.app}>
        <div className={classes.toolbar}></div> {/*this will push the contents bellow the AppBar*/}
        {children}
      </div>
    </div>
  );
}
