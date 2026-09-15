package com.example.assignment2.util

data class AnimalExtraInfo(
    val didYouKnow: String,
    val moreAbout: String
)

object AnimalInfoProvider {

    fun getExtraInfo(species: String): AnimalExtraInfo {

        return when (species) {

            "African Elephant" -> {
                AnimalExtraInfo(
                    didYouKnow = "African elephants can communicate over long distances using very low-frequency sounds called infrasound. These sounds can travel much farther than normal calls and help elephants stay connected with family members that may be several kilometres away.",
                    moreAbout = "African elephants are highly intelligent and social animals that commonly live in close family groups led by an experienced female called a matriarch. Their trunks are remarkably versatile and are used for breathing, smelling, drinking, feeding and communication. Their large ears help release body heat in warm environments, while their strong social bonds allow family members to cooperate when protecting and caring for younger elephants."
                )
            }

            "Giant Panda" -> {
                AnimalExtraInfo(
                    didYouKnow = "Although giant pandas belong to the order Carnivora, bamboo makes up the vast majority of their diet. Because bamboo provides relatively little energy, pandas spend a large part of their day eating and can consume many kilograms of it.",
                    moreAbout = "Giant pandas are native to mountainous regions of central China where cool, moist forests provide abundant bamboo. Their powerful jaws and broad molar teeth help them crush tough bamboo stems, while a specialised wrist bone functions almost like a thumb for gripping food. Pandas are generally solitary animals and communicate through scent markings and vocalisations. Their distinctive black-and-white coat has made them one of the world's most recognisable wildlife species."
                )
            }

            "Blue Whale" -> {
                AnimalExtraInfo(
                    didYouKnow = "Blue whales are the largest animals known to have lived on Earth. Despite their enormous size, they feed mainly on tiny shrimp-like animals called krill, filtering huge quantities of seawater through specialised baleen plates.",
                    moreAbout = "Blue whales travel through the world's oceans and can migrate great distances between feeding and breeding areas. Their streamlined bodies allow them to move efficiently through the water despite their enormous size. They communicate using powerful low-frequency calls that can travel across long distances underwater. During productive feeding seasons, blue whales consume enormous amounts of krill to build the energy reserves needed for migration and reproduction."
                )
            }

            "Komodo Dragon" -> {
                AnimalExtraInfo(
                    didYouKnow = "Komodo dragons have an extraordinary sense of smell and use their forked tongues to collect chemical particles from the air. This ability helps them locate potential food from considerable distances.",
                    moreAbout = "Komodo dragons are powerful reptiles found naturally on several Indonesian islands. They are the largest living lizards and possess muscular bodies, strong limbs and sharp claws. As opportunistic predators, they can hunt a variety of animals and also feed on carrion. Komodo dragons rely on patience, camouflage and their highly developed senses when searching for food, making them extremely well adapted to the dry tropical environments they inhabit."
                )
            }

            "Emperor Penguin" -> {
                AnimalExtraInfo(
                    didYouKnow = "Male emperor penguins incubate a single egg on top of their feet during the harsh Antarctic winter. They protect it beneath a warm fold of skin while enduring extreme cold and long periods without feeding.",
                    moreAbout = "Emperor penguins are exceptionally adapted to life in Antarctica. Their dense feathers, body fat and social huddling behaviour help them survive severe temperatures and powerful winds. They are excellent swimmers and dive beneath the sea ice to hunt fish, squid and other marine prey. During breeding season, large colonies gather on stable sea ice, where parents cooperate through different stages of incubation and chick rearing."
                )
            }

            "Red Panda" -> {
                AnimalExtraInfo(
                    didYouKnow = "Red pandas have a specialised wrist bone that works like a false thumb, helping them grasp bamboo while feeding. Giant pandas independently developed a remarkably similar adaptation.",
                    moreAbout = "Red pandas inhabit cool temperate forests across parts of the eastern Himalayas and southwestern China. They are skilled climbers and spend much of their time in trees, using their long tails for balance and warmth. Although bamboo forms an important part of their diet, they may also eat fruit, insects and other foods. Their thick reddish fur provides insulation and helps them blend into moss-covered branches in their forest environment."
                )
            }

            "Platypus" -> {
                AnimalExtraInfo(
                    didYouKnow = "A platypus can hunt underwater with its eyes, ears and nostrils closed. Special receptors in its bill detect tiny electrical signals produced by the movements of prey hidden beneath the water.",
                    moreAbout = "The platypus is one of the world's most unusual mammals and is native to eastern Australia and Tasmania. It spends much of its time in freshwater rivers and streams, where its webbed feet make it an efficient swimmer. Platypuses search underwater for insects, larvae and other small prey before returning to the surface to eat. Along with echidnas, they belong to the rare group of mammals known as monotremes, which reproduce by laying eggs."
                )
            }

            else -> {
                AnimalExtraInfo(
                    didYouKnow = "Every wildlife species has unique adaptations that help it survive within its natural environment.",
                    moreAbout = "Animals develop different behaviours, physical characteristics and survival strategies depending on their habitat, diet and environmental conditions."
                )
            }
        }
    }
}