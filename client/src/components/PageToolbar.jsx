import React, { useState } from "react";

import {
  AppBar,
  Container,
  Toolbar,
  Box,
  Typography,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Menu,
  MenuItem,
} from "@mui/material";

import MenuIcon from "@mui/icons-material/Menu";
import HelpIcon from "@mui/icons-material/Help";
import GitHubIcon from "@mui/icons-material/GitHub";

export default function PageToolbar() {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const menuIsOpen = Boolean(anchorEl);
  const [infoDialogIsOpen, setInfoDialogIsOpen] = useState(false);
  const [gitHubDialogIsOpen, setGitHubDialogIsOpen] = useState(false);

  const handleOpenMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleOpenInfoDialog = () => {
    setInfoDialogIsOpen(true);
  };

  const handleOpenGitHubDialog = () => {
    setGitHubDialogIsOpen(true);
  };

  const handleCloseMenu = () => {
    setAnchorEl(null);
  };

  const handleCloseDialog = () => {
    setAnchorEl(null);
    setInfoDialogIsOpen(false);
    setGitHubDialogIsOpen(false);
  };

  return (
    <>
      <Container maxWidth="lg">
        <AppBar position="static">
          <Toolbar>
            <Box sx={{ flex: 1 }} />

            <Box sx={{ flex: 1, textAlign: "center" }}>
              <Typography variant="h5" noWrap>
                Weather Twin
              </Typography>
            </Box>

            <Box sx={{ flex: 1, display: "flex", justifyContent: "flex-end" }}>
              {/*This icon opens an app menu*/}
              <IconButton
                aria-controls={menuIsOpen ? "app-menu" : undefined}
                aria-haspopup="true"
                aria-expanded={menuIsOpen ? "true" : undefined}
                onClick={handleOpenMenu}
              >
                <MenuIcon fontSize="large" style={{ color: "white" }} />
              </IconButton>
              <Menu
                anchorEl={anchorEl}
                open={menuIsOpen}
                onClose={handleCloseMenu}
                anchorOrigin={{
                  vertical: "bottom",
                  horizontal: "right",
                }}
                transformOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
              >
                {/*This icon opens a dialog for info about the app*/}
                <MenuItem
                  aria-label="open an info box"
                  onClick={handleOpenInfoDialog}
                >
                  <HelpIcon style={{ color: "black", marginRight: 10 }} />
                  <Typography variant={"h6"}>Info</Typography>
                </MenuItem>

                {/*This icon opens a dialog for the app's GitHub repositories*/}
                <MenuItem
                  aria-label="open an info box"
                  onClick={handleOpenGitHubDialog}
                >
                  <GitHubIcon
                    style={{
                      color: "black",
                      marginRight: 10,
                    }}
                  />
                  <Typography variant={"h6"}>GitHub</Typography>
                </MenuItem>
              </Menu>
            </Box>

            {/*This is an info dialog to tell the user what the app is about and how to use it*/}
            <Dialog open={infoDialogIsOpen} onClose={handleCloseDialog}>
              <DialogTitle>Weather Twin: the what and the how</DialogTitle>
              <DialogContent>
                <Typography gutterBottom={true}>
                  Weather Twin is an app that finds you a location that has a
                  similar weather as your location.
                </Typography>
                <Typography gutterBottom={true}>
                  Simply start typing your city in the input field, choose your
                  city from the list and select your desired temperature unit:
                  celsius or fahrenheit.
                </Typography>
                <Typography>Find your weather twin in seconds!</Typography>
              </DialogContent>
              <DialogActions>
                <Button variant="contained" onClick={handleCloseDialog}>
                  Got it!
                </Button>
              </DialogActions>
            </Dialog>

            {/*This is a dialog to show the user links to the app's GitHub repositories*/}
            <Dialog open={gitHubDialogIsOpen} onClose={handleCloseDialog}>
              <DialogTitle>Weather Twin on GitHub</DialogTitle>
              <DialogContent>
                <Typography gutterBottom={true}>
                  Weather Twin is a full-stack application with back-end and
                  front-end. Both have their own repositories on GitHub where
                  you can find the commented source code as well as READMEs.
                </Typography>
                <Typography gutterBottom={true}>
                  <Button
                    variant="contained"
                    aria-label="link to github repository for front-end"
                    onClick={(event) =>
                      window.open(
                        "https://github.com/devaajanne/weathertwin-server",
                      )
                    }
                  >
                    Click here to see the app's back-end repository on GitHub
                  </Button>
                </Typography>
                <Typography gutterBottom={true}>
                  <Button
                    variant="contained"
                    aria-label="link to github repository for front-end"
                    onClick={(event) =>
                      window.open(
                        "https://github.com/devaajanne/weathertwin-client",
                      )
                    }
                  >
                    Click here to see the app's front-end repository on GitHub
                  </Button>
                </Typography>
              </DialogContent>
              <DialogActions>
                <Button variant="contained" onClick={handleCloseDialog}>
                  Back to the app
                </Button>
              </DialogActions>
            </Dialog>
          </Toolbar>
        </AppBar>
      </Container>
    </>
  );
}
