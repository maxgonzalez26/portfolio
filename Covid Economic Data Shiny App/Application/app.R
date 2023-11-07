library(shiny)
library(ggplot2)
library(shinydashboard)
library(shinythemes)
library(tidyverse)
library(DT)
library(ggrepel)
library(plotly)
library(maps)
library(leaflet)
library(sf)

########## Pulling In Wrangled Data ######### 
covid_data <- readRDS("data/result.Rds") 

total_cases <- sum(covid_data$Cases1) 
tC <- format(total_cases, big.mark = ",")

total_deaths <- sum(covid_data$Deaths1)
tD <- format(total_deaths, big.mark = ",")

#Necessary for the barchart interactive component
barchart_choice_values <- c("AvgExpenditurePreCovid2019","Expenditure2020","SpendingDifference2020Other")
barchart_choice_names <- c("Avg CHE as % GDP 2019","Avg CHE as % GDP 2020","Difference in CHE 2020-2019")
names(barchart_choice_values) <- barchart_choice_names

#Necessary for the map interactive dataset joining
countries <- st_read("data/maps/world-administrative-boundaries.shp") |>
  rename("country" = name)
countries$country[countries$country == "Russian Federation"] <- "Russia"
countries$country[countries$country == "Iran (Islamic Republic of)"] <- "Iran"
countries$country[countries$country == "Libyan Arab Jamahiriya"] <- "Libya"
countries$country[countries$country == "Syrian Arab Republic"] <- "Syria"

# Merge data
map_data <- left_join(countries, covid_data, by = "country") # Assumes a 'country' column in both datasets

##################Setting Up Dashboard#############################

sidebar <- dashboardSidebar( #Establishing organizational structure of ShinyApp
  sidebarMenu(
    menuItem("Dashboard", tabName = "dashboard", icon = icon("dashboard")),
    menuItem("Map", icon = icon("map"), tabName = "map"),
    menuItem("Bar Chart", icon = icon("th"), tabName = "barchart"))
  )

body <- dashboardBody(
  tabItems(
    #Alex
    tabItem(tabName = "dashboard",
      h4("INFORMATION AS OF FRIDAY, NOV 3, 2023"),
      fluidRow(
        valueBoxOutput("totalCasesBox", width = 4),
        valueBoxOutput("totalDeathsBox", width = 4),
        valueBoxOutput("totalGDPBox", width = 4)
      ),
      sidebarLayout(
        sidebarPanel(
          selectInput("country", "Select a Country", choices = c("All", unique(covid_data$country)))# defaults to selecting all; can choose by country
        ),
      #Max 
      mainPanel(
        plotlyOutput("scatterplot1")
        )
      ),
      h4("Source: Our World in Data, NHA")
    ),
       tabItem(tabName = "map",
        leafletOutput("covidMap"),
        h4("Source: Our World in Data")
        
      ),
    #Ethan
    tabItem(tabName = "barchart",
            tabPanel(
              title = "Interactive Bar Chart",
              sidebarLayout(
                sidebarPanel(
                  radioButtons(inputId = "barchoice",
                               label = "Choose Variable to Display: ",
                               choices = barchart_choice_values,#choices for radiobuttons
                               selected = "AvgExpenditurePreCovid2019"),
                selectInput(inputId = "countries1",
                           label = "Select Countries:",
                           choices = unique(covid_data$country),#choices for "Select Countries"
                           #choices = c("All", unique(covid_data$country)),
                           multiple = TRUE,
                           selected = unique(covid_data$country))#defaults to selecting all, ctrl+A+backspace to clear
                           #selected = "All"
                ),
                mainPanel(
                  plotlyOutput(outputId = "barchart")
                )
              )
            ), 
            h4("Source: World Health Organization", class = 'right-align')
    )
  )
)

#Create User Interface
ui <- dashboardPage(
  dashboardHeader(title = "Covid Economic Data"),
  sidebar,
  body
)
  

#Back-end data 
server <- function(input, output) {
    country_data <- reactive({
      if(input$country == "All") {
        data <- covid_data
      } else {
        data <- covid_data[covid_data$country == input$country, ]
      }
      return(data)
    })
    
    #Alex
    output$totalCasesBox <- renderValueBox({
      total_cases <- sum(country_data()$Cases1, na.rm = TRUE)
      formatted_cases <- format(total_cases, big.mark = ",")
      valueBox(formatted_cases, width = 9, "Total Cases", icon = icon("globe"), color = "orange")
    })
    
    output$totalDeathsBox <- renderValueBox({ 
      total_deaths <- sum(country_data()$Deaths1, na.rm = TRUE)
      formatted_deaths <- format(total_deaths, big.mark = ",")
      valueBox(formatted_deaths, width = 9, "Total Deaths", icon = icon("skull"), color = "red")
    })
    
    output$totalGDPBox <- renderValueBox({
      total_GDP <- sum(country_data()$avg_gdp, na.rm = TRUE)
      formatted_GDP <- format(total_GDP, big.mark = ",")
      valueBox(formatted_GDP, width = 9, "Average GDP (2000-2019)", icon = icon("credit-card"), color = "green")
    })
    
    #Max
    output$scatterplot1 <- renderPlotly({
      data_filtered <- country_data() %>%
        filter(grepl(country, country, ignore.case = TRUE))
      
      plot <- plot_ly(data = data_filtered, x = ~avg_gdp, y = ~Deaths_per_M, 
                      text = ~paste("Country: ", country, "<br>Deaths per Million: ", Deaths_per_M, "<br>Avg GDP(2000-19): ", avg_gdp),
                      type = 'scatter', mode = 'markers', marker = list(color = 'blue'))
      
      plot <- plot %>%
        layout(
          title = "Comparison of Wealth and Mortality Rates",
          xaxis = list(title = "Average GDP(2000-19)"),
          yaxis = list(title = "Deaths per Million")
        )
      
      return(plot)
    })
    
    #Alex
    output$covidMap <- renderLeaflet({
      leaflet(map_data) %>% 
        addProviderTiles("CartoDB.Positron") %>% 
        addPolygons(
          fillColor = ~colorQuantile("YlOrRd", map_data$Cases1)(map_data$Cases1),
          weight = 2,
          opacity = 1,
          color = "white",
          fillOpacity = 0.7,
          highlight = highlightOptions(
            weight = 5,
            color = "#666",
            fillOpacity = 0.7,
            bringToFront = TRUE
          ),
          label = ~paste0("Country: ", map_data$country, "<br> Cases: ", map_data$Cases1)
        ) %>% 
        addLegend(
          pal = colorQuantile("YlOrRd", map_data$Cases1),
          values = ~map_data$Cases1,
          title = "Number of Cases"
        )
    })
    
    #Ethan
    output$barchart <- renderPlotly({
  
      filtered_data <- covid_data[covid_data$country %in% input$countries1, ]#take input from select box
      
      p <- plot_ly(data = filtered_data, x = ~country, y = ~get(input$barchoice), #apply filtered data to visual
                   type = "bar",
                   marker = list(color = "blue")) |>
        layout(xaxis = list(title = "Country", automargin=TRUE), yaxis = list(title = input$barchoice, automargin=TRUE), title = "CHE as % of GDP Versus Country")
      
      p
    })
}

  
shinyApp(ui, server)
