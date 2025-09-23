import {
  Card,
  CardHeader,
  Avatar,
  CardContent,
  Typography,
  CardActions,
  Button,
} from "@mui/material";

export default function WeatherLocationNotFoundCard() {
  return (
    <>
      <Card
        sx={{
          width: 275,
          height: 425,
          display: "flex",
          flexDirection: "column",
        }}
      >
        <CardHeader
          sx={{ textAlign: "left" }}
          avatar={
            <Avatar
              sx={{
                width: 40,
                height: 40,
                fontSize: "2.5rem",
              }}
            >
              🏙️
            </Avatar>
          }
          titleTypographyProps={{ variant: "h5" }}
          subheaderTypographyProps={{ color: "black" }}
          title="City"
          subheader="Country"
        ></CardHeader>

        <CardContent
          sx={{
            alignItems: "center",
            justifyContent: "center",
            textAlign: "center",
            height: "100%",
          }}
        >
          <Typography variant={"h5"}>Weather data not found</Typography>
          <Typography variant={"h5"}>Try another location!</Typography>
        </CardContent>

        <CardActions sx={{ justifyContent: "center" }}>
          <Button
            sx={{ width: "90%", mb: 1 }}
            variant="contained"
            disabled
            size="small"
            aria-label="link to location's full weather"
          >
            See full weather
          </Button>
        </CardActions>
      </Card>
    </>
  );
}
