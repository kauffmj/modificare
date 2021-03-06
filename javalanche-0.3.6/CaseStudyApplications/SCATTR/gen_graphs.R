## This file is part of SCATTR
##
## SCATTR is free software:  you can redistrubite it and/or modify it under the 
## terms of the GNU General Public License as published by the Free Software 
## Foundation, either version 3 of the License, or any later version.
##
## SCATTR is distributed in the hope that it will be useful, but WITHOUT ANY 
## WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
## FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
## details.
##
## You should have received a copy of the GNU General Public License along with 
## Foobar.  If not, see <http://www.gnu.org/licenses/>.
##
## Copyright 2007

# Load libraries for histograms and bar charts
library(gplots)
library(plotrix)

# Load R functions
source("Visualizer.R")

# r = scatter plots, linear regression on
# s = scatter plots, regression off
# h = histograms
# b = bar graphs

# if no args supplied, run all graphs, regression on
if(length(commandArgs()) < 6) {
	Create_Scatter_Plots(T)
	Create_Histo()
	Create_Bars()	
	q()
}

if(commandArgs()[6] == "r") {
	Create_Scatter_Plots(T)
}

if(commandArgs()[6] == "s") {
	Create_Scatter_Plots(F)
}

if(commandArgs()[6] == "h") {
	Create_Histo()
}

if(commandArgs()[6] == "b") {
	Create_Bars()
}
