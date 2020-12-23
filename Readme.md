# Evolution Generator
> Java evolution simulator, that gives statistics and allows user to control its flow
> <br> The project is based on Object Programming assignment and implements [Requirements] 



## 🔨 Installation and running


* OS X & Linux & Windows:

```git
git clone https://github.com/dimamik/AGH_Evolution_Generator.git
```

* Go to project folder and build project
 * Using IntelliJ (in case of problems with initial build):
   1. Go to File -> Settings -> Build,Execution,Deployment -> Build Tools -> Gradle 
   2. Change Gradle JVM from Gradle 1.8 to Gradle 15
   3. Aplly and run AGH_Evoluion_Generator [run]


## 📝 Map Notation

| Image on the map  | Description |
| --- | --- |
| ![](images\alive_animal.png)  | Alive Healthy Animal  |
| ![](images\alive_sick_animal.png)  | Alive Sick Animal  |
| ![](images\almost_dead_animal.png)  | Alive Almost Dead Animal |
| ![](images\dead_selected.png)  | Animal that was selected being alive and now dead | 
| ![](images\dominant_genome_animal.png)  | Animal with dominant genome | 
| ![](images\selected_animal.png)  | Selected Animal | 
| ![](images\grass.png)  | Grass | 


## 💡 Usage Tips

#### At any time of animation you can use:

* <b>"Show two maps checkbox"</b> - shows second map
* <b> "New Day button" </b>- generates new day in our simulation (better to use on stopped animation to see effects)
* <b> "Show dominant genome checkbox"</b> - stops simulation if running and shows animals with dominant genome as average dominant genome on map
* <b>"Write Statistics button"</b> - stops simulation if running and writes average statistics to statistics.json 
* <b> "Click on any animal on the map"</b> - (easier to use when animation is stopped) stops simulation if running and gives you an option to provide days number from the current day to accumulate statistics from 

#### At any time of animation you can follow (on the right side):

* <b>"Day of animation"</b> - current day of simulation
* <b> "Animals alive" </b>- shows alive animals on the map
* <b> "Total grass amount" </b>- shows total grass amount on the map
* <b> "Average genomes in genotype of alive animals" </b>- shows average genotype array, where <b>array[i]</b> is an average number of gens in <b>i-th</b> genome representing rotation (0 - forward, ... , 4 - backwards) counting all alive animals. <br> For
Example:
array[0] = 5 shows that for genome type 0 (forward move) average number of this genome in genotype is 5 <br>
For example "average" Animal would have in its 32-genome genotype 5 gens of 0-move
* <b> "Average energy for alive animals" </b>- shows average energy for alive animals
* <b> "Average live duration for dead animals" </b>- shows average live duration for dead animals
* <b> "Average kids number for alive animals" </b>- shows average kids number for alive animals


#### When animal is selected, you can follow:

* <b>"Genome types list"</b> - shows genotype array, where <b>array[i]</b> is the number of gens in <b>i-th</b> genome representing rotation (0 - forward, ... , 4 - backwards).<br> For
Example:
array[0] = 5 represents 5 gens of forward orientation in Animal genome

* <b> "All kids in past n days" </b>- Shows number of kids of selected Animal which were born in last n days from current day
* <b> "All Descedants in past n days"</b> - shows number of descedants (children of children and so on) which where born at last n days from current day
* <b>"Energy"</b> - Energy of Selected Animal 


## 🔍 Demonstation of basic simulation funcions
![demonstation]
## 🔍 Demonstation of working on two maps
![two_maps_demonstration]

## 👽 Contributing

1. Fork it (<https://github.com/dimamik/AGH_Evolution_Generator/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

## ✍️  Authors

Developed by – [@dimamik](https://github.com/dimamik) 

<!-- Markdown link & img dfn's -->
[two_maps_demonstration]: images/two_maps.gif
[demonstation]: images/basics.gif
[Requirements]: https://github.com/apohllo/obiektowe-lab/tree/master/lab8
[npm-image]: https://img.shields.io/npm/v/datadog-metrics.svg?style=flat-square
[npm-url]: https://npmjs.org/package/datadog-metrics
[npm-downloads]: https://img.shields.io/npm/dm/datadog-metrics.svg?style=flat-square
[travis-image]: https://img.shields.io/travis/dbader/node-datadog-metrics/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/dbader/node-datadog-metrics
[wiki]: https://github.com/yourname/yourproject/wiki
