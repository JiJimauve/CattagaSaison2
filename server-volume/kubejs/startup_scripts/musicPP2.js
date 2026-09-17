/*StartupEvents.registry('item', event => {

	event.create('pp2_disk_lew')
		.displayName('Is_Lew - Il se passe des choses ...')
		.musicDisc('song.pp2.ilsepassedeschoses_islew')
		.texture('kubejs:music_disc_pp2')
		.maxStackSize(1)



}) */

console.info('Music PP2 : V1.2.0')

StartupEvents.registry('sound_event', e => {
        e.create(`music.ilsepassedeschoses_islew`);
		
// -------------------------------
		e.create(`music.gettingthere2_bricolesbo`);
		e.create(`music.gettingthere1_bricolesbo`);
		e.create(`music.gettingthere_bricolesbo`);
// -------------------------------


// -------------------------------
		e.create(`music.chasingwhat_padeur`);
		e.create(`music.kssss_padeur`);
// -------------------------------

// -------------------------------
		e.create(`music.detoutlesmots_bricolesbo`);
		e.create(`music.detoutlesmots1_bricolesbo`);
		e.create(`music.detoutlesmots2_bricolesbo`);
		e.create(`music.detoutlesmots3_bricolesbo`);
		e.create(`music.detoutlesmots23_bricolesbo`);
		
		e.create(`music.detoutlesmots1l_bricolesbo`);
// -------------------------------



// -------------------------------
		e.create(`music.tinybitofwarmth_bricolesbo`);
		e.create(`music.forgotmyshovel_roguie`);
		e.create(`music.theofficebardcore_zakium`);
		e.create(`music.catboyinspace_tiluh`);
		e.create(`music.balladedansespace_jimauve`);
		e.create(`music.dog_in_space_wiouhh`);
// -------------------------------
		e.create(`music.ane_trotro_du_tiek`);
		e.create(`music.hymne_urss`);
		e.create(`music.hhou_song_jimauve`);
		e.create(`music.mamy_song_get_cosy`);
		e.create(`music.cite_dor_drill_lotte`);
// -------------------------------
		e.create(`music.bricolesbo_2025_10_11_r3`);
		e.create(`music.bricolesbo_2025_11_06_r9`);
		e.create(`music.bricolesbo-aaaa`);
  
// -------------------------------

		e.create(`music.lena-kazu`);
		e.create(`music.lena-bonanniversaire`);
  
})
  

StartupEvents.registry('item', e => {
	
	e.create(`music_disc_ilsepassedeschoses_islew`, "music_disc")
            .song(`kubejs:music.ilsepassedeschoses_islew`, 12) // Song Call -- Duration in sec
            .displayName('Is_Lew Song') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json : like that : 
			//		item.kubejs.music_disc_ilsepassedeschoses_islew.desc: "Le célèbre tube d'Is_lew",

// -------------------------------
	e.create(`music_disc_bricolesbo_getting_there2`, "music_disc")
            .song(`kubejs:music.gettingthere2_bricolesbo`, 42) // Song Call -- Duration in sec
            .displayName('BricoLesbo - Getting There (part2)') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
	e.create(`music_disc_bricolesbo_getting_there1`, "music_disc")
            .song(`kubejs:music.gettingthere1_bricolesbo`, 76) // Song Call -- Duration in sec
            .displayName('BricoLesbo - Getting There (part1)') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
	e.create(`music_disc_bricolesbo_getting_there`, "music_disc")
            .song(`kubejs:music.gettingthere_bricolesbo`, 118) // Song Call -- Duration in sec
            .displayName('BricoLesbo - Getting There') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
// -------------------------------

// -------------------------------

	e.create(`music_disc_detoutlesmots_bricolesbo`, "music_disc")
            .song(`kubejs:music.detoutlesmots_bricolesbo`, 239) // Song Call -- Duration in sec
            .displayName('BricoLesbo - De Tout les mots') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_drill`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
						
	e.create(`music_disc_detoutlesmots1l_bricolesbo`, "music_disc")
            .song(`kubejs:music.detoutlesmots1l_bricolesbo`, 51) // Song Call -- Duration in sec
            .displayName('BricoLesbo - De Tout les mots (1L)') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_red`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
	e.create(`music_disc_detoutlesmots1_bricolesbo`, "music_disc")
            .song(`kubejs:music.detoutlesmots1_bricolesbo`, 51) // Song Call -- Duration in sec
            .displayName('BricoLesbo - De Tout les mots (1)') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_red`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
	e.create(`music_disc_detoutlesmots2_bricolesbo`, "music_disc")
            .song(`kubejs:music.detoutlesmots2_bricolesbo`, 27) // Song Call -- Duration in sec
            .displayName('BricoLesbo - De Tout les mots (2)') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_red`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
					
	e.create(`music_disc_detoutlesmots3_bricolesbo`, "music_disc")
            .song(`kubejs:music.detoutlesmots3_bricolesbo`, 156) // Song Call -- Duration in sec
            .displayName('BricoLesbo - De Tout les mots (3)') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_red`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
						
	e.create(`music_disc_detoutlesmots23_bricolesbo`, "music_disc")
            .song(`kubejs:music.detoutlesmots23_bricolesbo`, 184) // Song Call -- Duration in sec
            .displayName('BricoLesbo - De Tout les mots (2&3)') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_red`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
		
			
// -------------------------------
		
	e.create(`music_disc_tinybitofwarmth_bricolesbo`, "music_disc")
            .song(`kubejs:music.tinybitofwarmth_bricolesbo`, 124) // Song Call -- Duration in sec
            .displayName('BricoLesbo - Tiny bit of warmth') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
	e.create(`music_disc_forgotmyshovel_roguie`, "music_disc")
            .song(`kubejs:music.forgotmyshovel_roguie`, 121) // Song Call -- Duration in sec
            .displayName('Roguie - Forgot my shovel') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
	e.create(`music_disc_theofficebardcore_zakium`, "music_disc")
            .song(`kubejs:music.theofficebardcore_zakium`, 31) // Song Call -- Duration in sec
            .displayName('Zakium - The Office BardCore') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
			
									
	e.create(`music_disc_catboyinspace_tiluh`, "music_disc")
            .song(`kubejs:music.catboyinspace_tiluh`, 81) // Song Call -- Duration in sec
            .displayName('Tiluh - Catboy in space') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
	e.create(`music_disc_balladedansespace_jimauve`, "music_disc")
            .song(`kubejs:music.balladedansespace_jimauve`, 107) // Song Call -- Duration in sec
            .displayName('Jimauve - Ballade dans l\'espace') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
	e.create(`music_disc_dog_in_space_wiouhh`, "music_disc")
            .song(`kubejs:music.dog_in_space_wiouhh`, 73) // Song Call -- Duration in sec
            .displayName('Wiouhh - Dog in space') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
// -------------------------------


	e.create(`music_disc_chasingwhat_padeur`, "music_disc")
            .song(`kubejs:music.chasingwhat_padeur`, 120) // Song Call -- Duration in sec
            .displayName('Padeure - ChasingWhat') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
	e.create(`music_disc_kssss_padeur`, "music_disc")
            .song(`kubejs:music.kssss_padeur`, 131) // Song Call -- Duration in sec
            .displayName('Padeure - Kssss') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 

// -------------------------------

	e.create(`music_disc_ane_trotro_du_tiek`, "music_disc")
            .song(`kubejs:music.ane_trotro_du_tiek`, 54) // Song Call -- Duration in sec
            .displayName('XEBOX - Ane Trotro du TIEK') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_drill`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 

	e.create(`music_disc_hymne_urss`, "music_disc")
            .song(`kubejs:music.hymne_urss`, 222) // Song Call -- Duration in sec
            .displayName('Hymne National De L\'URSS') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_drill`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
	e.create(`music_disc_hhou_song_jimauve`, "music_disc")
            .song(`kubejs:music.hhou_song_jimauve`, 34) // Song Call -- Duration in sec
            .displayName('Jimauve - H H O U song') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_drill`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
	e.create(`music_disc_mamy_song_get_cosy`, "music_disc")
            .song(`kubejs:music.mamy_song_get_cosy`, 46) // Song Call -- Duration in sec
            .displayName('Get_Cosy - Mamy Song') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_drill`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 		
						
	e.create(`music_disc_cite_dor_drill_lotte`, "music_disc")
            .song(`kubejs:music.cite_dor_drill_lotte`, 63) // Song Call -- Duration in sec
            .displayName('Lotte - Cité d\'or REMIX DRILL') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_drill`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
			
// -------------------------------
						
	e.create(`music_disc_bricolesbo_2025_10_11_r3`, "music_disc")
            .song(`kubejs:music.bricolesbo_2025_10_11_r3`, 86) // Song Call -- Duration in sec
            .displayName('BricoLesbo - 2025 10 11 R3') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
	e.create(`music_disc_bricolesbo_2025_11_06_r9`, "music_disc")
            .song(`kubejs:music.bricolesbo_2025_11_06_r9`, 98) // Song Call -- Duration in sec
            .displayName('BricoLesbo - 2025 11 06 R9') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
						
	e.create(`music_disc_bricolesbo-aaaa`, "music_disc")
            .song(`kubejs:music.bricolesbo-aaaa`, 37) // Song Call -- Duration in sec
            .displayName('BricoLesbo - AAAA') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_drill`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.json 
			
			
								
	e.create(`music_disc_lena-kazu`, "music_disc")
            .song(`kubejs:music.lena-kazu`, 58) // Song Call -- Duration in sec
            .displayName('Lenacadabra - tentative, amour et kevin') // Name of Item
            .texture(`kubejs:item/music_disc_pp2_drill`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.jso
			
								
	e.create(`music_disc_lena-bonanniversaire`, "music_disc")
            .song(`kubejs:music.lena-bonanniversaire`, 129) // Song Call -- Duration in sec
            .displayName('Lenacadabra - Bon anniversaire') // Name of Item
            .texture(`kubejs:item/music_disc_pp2`) // put textures file to 
            .tag("music_discs") // To be played
			// add desc in en_us.jso

  
 });
