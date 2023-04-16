/*******************************************************************************
 * Copyright (c) 2012 mrbrutal. All rights reserved.
 *
 * @name TrainCraft
 * @author mrbrutal
 ******************************************************************************/

package train.common.library;

import net.minecraft.item.Item;

public enum ItemIDs {
	diesel("ItemContainer", "item_diesel_filled", 20),
	stake("ItemStacked", "item_stake", 1),
	steeldust("ItemTrain", "item_steeldust", 0),
	coaldust("ItemTrain", "item_coaldust", 15),
	graphite("ItemTrain", "item_graphite", 10),
	boiler("ItemTrain", "item_boiler_steel", 5),
	firebox("ItemTrain", "item_firebox_steel", 5),
	bogie("ItemTrain", "item_bogie_steel", 4),
	steelframe("ItemTrain", "item_frame_steel", 6),
	steelcab("ItemTrain", "item_cab_steel", 5),
	steelchimney("ItemTrain", "item_chimney_steel", 5),
	airship("ItemZeppelin", "item_zeppelin", 1),
	balloon("ItemTrain", "item_balloon", 7),
	propeller("ItemTrain", "item_propeller", 6),
	steamengine("ItemTrain", "item_engine_steam", 2),
	dieselengine("ItemTrain", "item_engine_diesel", 1),
	piston("ItemTrain", "item_piston", 9),
	camshaft("ItemTrain", "item_camshaft", 8),
	cylinder("ItemTrain", "item_cylinder", 7),
	electmotor("ItemTrain", "item_engine_electric", 1),
	woodenFrame("ItemTrain", "item_frame_wood", 12),
	woodenBogie("ItemTrain", "item_bogie_wood", 15),
	woodenCab("ItemTrain", "item_cab_wood", 10),
	ironChimney("ItemTrain", "item_chimney_iron", 7),
	ironFirebox("ItemTrain", "item_firebox_iron", 4),
	ironBoiler("ItemTrain", "item_boiler_iron", 5),
	ironFrame("ItemTrain", "item_frame_iron", 6),
	ironBogie("ItemTrain", "item_bogie_iron", 8),
	ironCab("ItemTrain", "item_cab_iron", 5),
	steel("ItemTrain", "item_steel", 4),
	refinedFuel("ItemContainer", "item_fuel_filled", 15),
	signal("ItemSignal", "item_signal", 0),
	seats("ItemTrain", "item_seats", 15),
	transformer("ItemTrain", "item_transformer", 4),
	controls("ItemTrain", "item_controls", 7),
	transmition("ItemTrain", "item_transmition", 5),
	generator("ItemTrain", "item_generator", 5),
	overalls("ItemTCArmor", "armour_overalls", 1),
	jacket("ItemTCArmor", "armour_jacket", 1),
	hat("ItemTCArmor", "armour_hat", 1),
	rawPlastic("ItemTrain", "item_plastic", 20),
	ingotCopper("ItemTrain", "item_copper", 9),
	copperWireFine("ItemTrain", "item_wire", 4),
	electronicCircuit("ItemTrain", "item_circuit", 2),
	chunkLoaderActivator("ItemChunkLoaderActivator", "item_chunk_loader", 1),
	//refinedFuelLiquid("ItemTrain", "item_liquid_fuel"),
	//dieselLiquid("ItemTrain", "item_liquid_diesel"),
	emptyCanister("ItemContainer", "item_canister", 40),
	//copperRail("ItemRail", "item_rail_copper", 1),
	//steelRail("ItemRail", "item_rail_steel", 1),
	copperRail("ItemTrain", "item_rail_copper", 1),
	steelRail("ItemTrain", "item_rail_steel", 1),
	recipeBook("ItemRecipeBook", "item_book_blue", 1),
	creditsBook("ItemRecipeBook", "item_book_brown", 1),
	adminBook("ItemAdminBook", "item_book_blue", 0),
	wirelessTransmitter("ItemWirelessTransmitter", "wireless_transmitter", 3),
	atoCard("ItemATOCard", "ato_card", 5),
	pennCentral("ItemPennCentralSimCard", "ato_card", 291),
	paintbrushThing("ItemPaintbrushThing", "paintbrushthing", 9),


	minecartWork("ItemRollingStock", "train_work", 4),
	minecartTender("ItemRollingStock", "train_tender", 4),
	minecartChest("ItemRollingStock", "train_freight_1", 4),
	minecartFreightBamboo("ItemRollingStock", "train_freight_bamboo", 2),
	minecartTrainBamboo("ItemRollingStock", "train_train_bamboo", 3),
	minecartPassengerBamboo("ItemRollingStock", "train_passenger_bamboo", 1),
	minecartFreightGermanPost("ItemRollingStock", "train_freight_german_post", 2),
	minecartFreightDepressedFlatbed("ItemRollingStock", "train_freight_depressed_flatbed", 2),
	minecartPower("ItemRollingStock", "train_steam_normal", 10),
	minecartASTFAutorack("ItemRollingStock", "train_autorack", 5),
	minecartLoco3("ItemRollingStock", "train_steam_small", 7),
	minecartBuilder("ItemRollingStock", "train_builder", 20),
	minecartCaboose("ItemRollingStock", "train_caboose", 6),
	minecartGrain("ItemRollingStock", "train_hopper_grain", 6),
	minecartWatertransp("ItemRollingStock", "train_tank_lava", 7),
	minecartWood("ItemRollingStock", "train_transport_wood", 3),
	minecartPassenger2("ItemRollingStock", "train_passenger_1", 2),
	minecartBR_E69("ItemRollingStock", "train_bre69", 10),
	minecartShunter("ItemRollingStock", "train_shunter", 10),
	minecartVL10("ItemRollingStock", "train_vl10", 10),
	minecartTramWood("ItemRollingStock", "train_tram", 7),
	minecartChmE3("ItemRollingStock", "train_chme3", 8),
	minecartHeavySteam("ItemRollingStock", "train_steam_heavy", 7), //HandleMaxAttachedCarts.HEAVY_STEAM_CARTS, 0D, (int) SpeedHandler.HEAVYSTEAM, "train_steam_heavy"),
	minecartTenderHeavy("ItemRollingStock", "train_tender_heavy", 5),
	minecartFlatCart("ItemRollingStock", "train_flat", 1),
	//minecartNewHighSpeedLoco("ItemRollingStock","train_speed",10),
	minecartFreightCart2("ItemRollingStock", "train_freight_2", 2),
	minecartOpenWagon("ItemRollingStock", "train_hopper", 3),
	minecartTankWagon("ItemRollingStock", "train_tank_us", 5),
	minecartJukeBoxCart("ItemRollingStock", "train_jukebox", 10),
	minecartStockCar("ItemRollingStock", "train_stock", 10),
	minecartDRWGStockCar("ItemRollingStock", "drwg_stockcar", 10),
	minecartCabooseWork("ItemRollingStock", "train_caboose_work", 3),
	minecartPassenger5("ItemRollingStock", "train_passenger_2", 2),
	minecartTankWagon2("ItemRollingStock", "train_tank_blue", 5),
	minecartPassengerBlue("ItemRollingStock", "train_passenger_3", 2),
	minecartGP7Red("ItemRollingStock", "train_gp7", 10),
	minecartCD742("ItemRollingStock", "train_cd742", 10),
	minecartFlatCartSU("ItemRollingStock", "train_flat_su", 1),
	minecartFlatCartUS("ItemRollingStock", "train_flat_us", 1),
	minecartBoxCartUS("ItemRollingStock", "train_box_us", 4),
	minecartBoxCartPRR("ItemRollingStock", "train_box_prr", 4),
	minecartFreightHopperUS("ItemRollingStock", "train_hopper_us", 6),
	minecartFreight100TonHopper("ItemRollingStock", "train_100_ton_hopper", 8),
	minecartTankWagonUS("ItemRollingStock", "train_tank_us_2", 8),
	minecartTankWagonThreeDome("ItemRollingStock", "train_tank_three_dome", 7),
	minecartFlatCartWoodUS("ItemRollingStock", "train_flat_wood", 3),
	minecartBulkheadFlatCart("ItemRollingStock", "train_bulkheadflat_wood", 3),
	minecartFreightCartUS("ItemRollingStock", "train_freight_3", 4),
	minecartFreightCartSmall("ItemRollingStock", "train_freight_4", 2),
	minecartLocoCherepanov("ItemRollingStock", "train_cherpanov", 4),
	minecartMineTrain("ItemRollingStock", "train_mine_cart", 1),
	minecartLocoMineTrain("ItemRollingStock", "train_mine_train", 2),
	minecartSteamRedTender("ItemRollingStock", "train_tender_steam", 4),
	minecartFreightWood2("ItemRollingStock", "train_transport_wood_2", 6),
	minecartCaboose3("ItemRollingStock", "train_caboose_2", 3),
	minecartGWRBrakeVan("ItemRollingStock", "train_gwr_brakevan", 3),
	minecartPassenger7("ItemRollingStock", "train_passenger_4", 2),
	minecartFreightClosed("ItemRollingStock", "train_freight_closed", 5),
	minecartFreightOpen2("ItemRollingStock", "train_freight_open", 6),
	minecartLocomogulBlue("ItemRollingStock", "train_mogul", 10),
	minecartLocoForneyRed("ItemRollingStock", "train_forney", 10),
	minecartLocoBR01_DB("ItemRollingStock", "train_br01", 15),
	minecartLocoCoranationClass("ItemRollingStock", "train_coronationclass", 15),
	minecartLocoCoranationClassTender("ItemRollingStock", "train_coronationclasstender", 15),
	minecartMailWagon_DB("ItemRollingStock", "train_mail", 4),
	minecartFreightWagon_DB("ItemRollingStock", "train_freight_db", 3),
	minecartPassenger8_1class_DB("ItemRollingStock", "train_passenger_5", 6),
	minecartPassenger9_2class_DB("ItemRollingStock", "train_passenger_6", 5),
	minecartLocoBR80_DB("ItemRollingStock", "train_br80", 10),
	minecartTenderBR01_DB("ItemRollingStock", "train_tender_br01", 15),
	minecartFlatCartRail_DB("ItemRollingStock", "train_transport_rail", 6),
	minecartFlatCart_DB("ItemRollingStock", "train_flat_db", 1),
	minecartFlatCartLogs_DB("ItemRollingStock", "train_transport_wood_3", 2),
	minecartPropagandaJapan("ItemRollingStock", "propaganda_japan", 2),
	minecartPropagandaBritish("ItemRollingStock", "propaganda_british", 2),
	minecartPropagandaUSSR("ItemRollingStock", "propaganda_ussr", 2),
	minecartPropagandaUs("ItemRollingStock", "propaganda_us", 2),
	minecartFreightGondola_DB("ItemRollingStock", "train_freight_gondola", 3),
	minecartFreightL("ItemRollingStock", "train_freight_l", 3),
	minecartTankWagon_DB("ItemRollingStock", "train_tank_db", 6),
	minecartLocoSD70("ItemRollingStock", "train_sd70", 10),
	minecartLocoBP4("ItemRollingStock", "train_bp4", 10),
	minecartHeavyweightFreight("ItemRollingStock", "train_heavyweight_freight", 10),
	minecartLocoEMDF7("ItemRollingStock", "train_emdf7", 10),
	minecartLocoEMDF3("ItemRollingStock", "train_emdf3", 10),
	minecartLocoEMDF7B("ItemRollingStock", "train_emdf7b", 10),
	minecartLocoEMDF3B("ItemRollingStock", "train_emdf3b", 10),
	minecartLocoDD35B("ItemRollingStock", "train_dd35b", 16),
	minecartLocoWLs40("ItemRollingStock", "train_wls40", 10),
	minecartRotativeDigger("ItemRotativeDigger","modelrotaryexcavator_icon",10),
	minecartLocoEr("ItemRollingStock", "train_ussr", 10),
	minecartTenderEr("ItemRollingStock", "train_tender_ussr", 4),
	minecartTenderC41("ItemRollingStock", "train_tender_c41", 4),
	minecartLocoC41("ItemRollingStock", "train_c41", 6),
	minecartLocoC41_080("ItemRollingStock", "train_c41_080", 6),
	minecartLocoAlcoSC4("ItemRollingStock", "train_alcosc4", 6),
	minecartLocoC41T("ItemRollingStock", "train_c41t", 6),
	minecartLocoSouthern1102("ItemRollingStock", "train_southern1102", 6),
	minecartTenderSouthern1102("ItemRollingStock", "train_southern1102tender", 3),
	minecartLocoUSATCUS("ItemRollingStock", "train_S100US", 6),
	minecartLocoUSATCUK("ItemRollingStock", "train_S100UK", 6),
	//minecartLocoU57("ItemRollingStock","train_",10),
	//minecartTenderU57("ItemRollingStock","train_",10),
	//minecartLocoSD40("ItemRollingStock","train_",10),
	minecartFreightCenterBeam_Empty("ItemRollingStock", "train_freight_empty", 2),
	minecartFreightCenterBeam_Wood_1("ItemRollingStock", "train_freight_wood_1", 3),
	minecartFreightCenterBeam_Wood_2("ItemRollingStock", "train_freight_wood_2", 3),
	minecartFreightTrailer("ItemRollingStock", "train_freight_trailer", 5),
	minecartFreightWellcar("ItemRollingStock", "train_freight_wellcar", 5),
	minecartFreightGTNG("ItemRollingStock", "freight_gtng", 5),
	minecartHeavyweightMailcar("ItemRollingStock", "heavyweight_mailcar", 5),
	minecartKof_DB("ItemRollingStock", "train_kof", 10),
	minecartV60_DB("ItemRollingStock", "train_v60", 10),
	minecartLocoCD151("ItemRollingStock", "train_CD151", 3),
	minecartE10_DB("ItemRollingStock", "train_e10db", 10),
	minecartE103("ItemRollingStock", "train_e103", 10),
	minecartIC4_DSB_FG("ItemRollingStock", "train_ic4_dsb_fg", 10),
	minecartIC4_DSB_FH("ItemRollingStock", "train_ic4_dsb_fh", 10),
	minecartIC4_DSB_MG("ItemRollingStock", "train_ic4_dsb_mg", 10),
	minecartGS4_Loco("ItemRollingStock", "GS4_engine", 10),
	minecartGS4_Passenger("ItemRollingStock", "GS4_passenger", 10),
	minecartGS4_Baggage("ItemRollingStock", "GS4_baggage", 10),
	minecartGS4_Observatory("ItemRollingStock", "GS4_observatory", 10),
	minecartGS4_Tail("ItemRollingStock", "GS4_tail", 10),
	minecartGS4_Tender("ItemRollingStock", "GS4_tender", 10),
	minecart4000GallonTender("ItemRollingStock", "4000GallonTender", 10),
	minecarttenderBerk1225("ItemRollingStock", "BerkshireTender", 10),
	minecartLocoHallClass("ItemRollingStock", "HallClass", 3),
	minecartLocoBerk1225("ItemRollingStock", "Berkshire1225", 3),
	minecartLocoBerk765("ItemRollingStock", "Berkshire765", 3),
	minecartLocoFowler("ItemRollingStock", "Fowler 4F", 3),
	minecartFowler4FTender("ItemRollingStock", "Fowler 4F Tender", 3),
	minecartLocoKingClass("ItemRollingStock", "KingClass", 3),
	minecartLoco44TonSwitcher("ItemRollingStock", "train_44tonner", 10),
	minecartDenverRioGrandePassenger("ItemRollingStock", "denverriogrande_passenger", 10),
	minecartDenverRioGrandeCombo("ItemRollingStock", "denverriogrande_combo", 10),
	minecartDenverRioGrandeBaggage("ItemRollingStock", "denverriogrande_baggage", 10),
	//minecartMILW_H1044("ItemRollingStock", "MILW_H1044", 10),
	minecartSlateWagon("ItemRollingStock", "slatewagon", 10),
	minecartIceWagon("ItemRollingStock", "icewagon", 10),
	minecartICE1_Loco("ItemRollingStock", "train_ice1", 10),
	minecartICE1_1stClass("ItemRollingStock", "train_ice1_passenger", 10),
	minecartICE1_2ndClass("ItemRollingStock", "train_ice1_passenger", 10),
	minecartICE1_Restaurant("ItemRollingStock", "train_ice1_restaurant", 10),

	hat_ticketMan_paintable("ItemTCArmor", "armor_ticket_man_hat", 1),
	pants_ticketMan_paintable("ItemTCArmor", "armor_ticket_man_pants", 1),
	jacket_ticketMan_paintable("ItemTCArmor", "armor_ticket_man_jacket", 1),
	hat_driver_paintable("ItemTCArmor", "armor_driver_hat", 1),
	pants_driver_paintable("ItemTCArmor", "armor_driver_pants", 1),
	jacket_driver_paintable("ItemTCArmor", "armor_driver_jacket", 1),

	helmet_suit_paintable("ItemTCArmor", "armor_composite_helmet", 1),
	pants_suit_paintable("ItemTCArmor", "armor_composite_pants", 1),
	boots_suit_paintable("ItemTCArmor", "armor_composite_boots", 1),
	jacket_suit_paintable("ItemTCArmor", "armor_composite_chest", 1),
	reinforcedPlastic("ItemTrain", "item_fiberglass_plate", 10),
	reinforcedPlates("ItemTrain", "item_reinforced_plate", 10),
	composite_wrench("ItemWrench", "item_composite_wrench", 1),

	minecartPassengerHighSpeedCarZeroED("ItemRollingStock", "train_high_speed_cart_zeroed", 14),
	minecartNYTram("ItemRollingStock", "train_tram_ny", 7),
	minecartLocoHighSpeedZeroED("ItemRollingStock", "train_high_speed_loco_zeroed", 17),
	minecartLocoA4Mallard("ItemRollingStock", "train_loco_a4_mallard", 14),
	minecartLocoA4MallardTender("ItemRollingStock", "train_loco_a4_mallard_tender", 5),
	minecartLocoC62Class("ItemRollingStock", "train_loco_c62_class", 14),
	minecartLocoD51Short("ItemRollingStock", "train_loco_d51s", 14),
	minecartLocoD51Long("ItemRollingStock", "train_loco_d51", 14),
	minecartTenderC62Class("ItemRollingStock", "train_tender_c62_class", 5),
	minecartTenderD51("ItemRollingStock", "train_tender_d51", 5),

	minecartLocoSteamShay("ItemRollingStock", "train_shay", 4),
	minecartLocoSteamVBShay("ItemRollingStock", "train_vbshay", 4),
	minecartLocoSteamClimax("ItemRollingStock", "train_climax", 4),
	minecartLocoSteamPannier("ItemRollingStock", "train_pannier", 4),
	minecartLocoSteamAdler("ItemRollingStock", "train_adler", 3),
	minecartLocoSteamAlice("ItemRollingStock", "train_alice", 3),
	minecartLocoSteamGLYN("ItemRollingStock", "train_glyn", 3),
	minecartLocoSteam262T("ItemRollingStock", "train_262t", 3),
	minecartLocoSteam040vb("ItemRollingStock", "train_040VB", 3),
	minecartLocoBR185("ItemRollingStock", "train_br101", 3),
	minecartLocoClass85("ItemRollingStock", "train_class85", 3),
	minecartLocoDeltic("ItemRollingStock", "train_deltic", 3),
	minecartLocoDD35A("ItemRollingStock", "train_dd35a", 3),
	minecartLocoSnowPlow("ItemRollingStock", "train_snowplow", 3),
	minecartLocoEWSClass66("ItemRollingStock", "train_ewsclass66", 3),
	minecartTenderAdler("ItemRollingStock", "train_tender_adler", 1),
	minecartPassengerAdler("ItemRollingStock", "train_passenger_adler", 2),
	minecartLocoFOLM1("ItemRollingStock", "train_fol_m1", 10),
	minecartKClassRailBox("ItemRollingStock", "train_k_class_railbox", 2),
	minecartLocoLSSP7("ItemRollingStock", "train_lssp7", 4),
	minecartLongCoveredHopper("ItemRollingStock", "train_longcoveredhopper", 3),
	minecartShortCoveredHopper("ItemRollingStock", "train_shortcoveredhopper", 2),

	minecartPassengerRheingold("ItemRollingStock", "train_passenger_rheingold", 5),
	minecartPassengerRheingoldDining1("ItemRollingStock", "train_passenger_rheingolddining1", 5),
	minecartPassengerRheingoldDining2("ItemRollingStock", "train_passenger_rheingolddining2", 5),
	minecartPassengerRheingoldPanorama("ItemRollingStock", "train_passenger_rheingoldpanorama", 5),

	minecartPassengerMILW("ItemRollingStock", "train_passenger_milw", 5),
	minecartPassengerMILWTail("ItemRollingStock", "train_passenger_milw_tail", 5),
	minecartBaggageMILW("ItemRollingStock", "train_baggage_milw", 5),
	//minecartPassengerMILWDiner("ItemRollingStock", "train_passenger_milw_diner", 5),
	minecartLocoMILWClassA("ItemRollingStock", "train_milw_class_a", 7),
	minecartMILWTender("ItemRollingStock", "train_milw_tender", 5),

	minecartCabooseLogging("ItemRollingStock", "train_logging_caboose", 1),
	minecartCabooseLoggingPRR("ItemRollingStock", "train_prr_caboose", 1),
	minecartPassengerTramNY("ItemRollingStock", "train_tram_ny", 3),

	minecartPassengerDBOriental("ItemRollingStock", "train_passenger_orient", 2),
	minecartLocoSD40("ItemRollingStock", "train_sd40", 10),
	zeppelin("ItemZeppelin", "item_zeppelin_one_balloon", 1),

	tcRailSmallStraight("ItemTCRail", "item_rail_straight_small", 5),
	tcRailMediumStraight("ItemTCRail", "item_rail_straight_medium", 5),
	tcRailLongStraight("ItemTCRail", "item_rail_straight_large", 5),
	tcRailSmallRoadCrossing("ItemTCRail", "item_rail_small_road_crossing", 5),
	tcRailSmallRoadCrossing1("ItemTCRail", "item_rail_small_road_crossing_1", 5),
	tcRailSmallRoadCrossing2("ItemTCRail", "item_rail_small_road_crossing_2", 5),

	tcRailMediumTurn("ItemTCRail", "item_rail_turn_medium", 5),
	tcRailLargeTurn("ItemTCRail", "item_rail_turn_large", 5),
	tcRailVeryLargeTurn("ItemTCRail", "item_rail_turn_veryLarge", 5),

	tcRailMediumSwitch("ItemTCRail", "item_rail_switch_medium", 5),
	tcRailLargeSwitch("ItemTCRail", "item_rail_switch_large", 5),
	tcRailMediumParallelSwitch("ItemTCRail", "item_rail_switch_parallel", 5),

	tcRailTwoWaysCrossing("ItemTCRail","item_rail_two_ways_crossing",5),
	tcRailSlopeWood("ItemTCRail", "item_rail_straight_slope_wood", 3),
	tcRailSlopeGravel("ItemTCRail", "item_rail_straight_slope_gravel", 3),
	tcRailSlopeBallast("ItemTCRail", "item_rail_straight_slope_ballast", 3),
	tcRailLargeSlopeWood("ItemTCRail", "item_rail_straight_slope_wood", 3),
	tcRailLargeSlopeGravel("ItemTCRail", "item_rail_straight_slope_gravel", 3),
	tcRailLargeSlopeBallast("ItemTCRail", "item_rail_straight_slope_ballast", 3),
	tcRailVeryLargeSlopeWood("ItemTCRail", "item_rail_straight_slope_wood", 3),
	tcRailVeryLargeSlopeGravel("ItemTCRail", "item_rail_straight_slope_gravel", 3),
	tcRailVeryLargeSlopeBallast("ItemTCRail", "item_rail_straight_slope_ballast", 3),
	tcRailSlopeSnowGravel("ItemTCRail", "item_rail_straight_slope_snow_gravel", 3),
	tcRailLargeSlopeSnowGravel("ItemTCRail", "item_rail_straight_slope_snow_gravel", 3),
	tcRailVeryLargeSlopeSnowGravel("ItemTCRail", "item_rail_straight_slope_snow_gravel", 3),
	tcRailSlopePeaGravel("ItemTCRail", "item_rail_straight_slope_pea_gravel", 3),
	tcRailLargeSlopePeaGravel("ItemTCRail", "item_rail_straight_slope_pea_gravel", 3),
	tcRailVeryLargeSlopePeaGravel("ItemTCRail", "item_rail_straight_slope_pea_gravel", 3),

	tcRailSuperLargeTurn("ItemTCRail", "item_rail_tc_super_large_turn", 5),
	tcRailVeryLongStraight("ItemTCRail", "item_rail_straight_very_large", 5),
	tcRailSmallParallelCurve("ItemTCRail", "item_rail_tc_parallel_curve_small", 3),
	tcRailMediumParallelCurve("ItemTCRail", "item_rail_tc_parallel_curve_medium", 3),
	tcRailLargeParallelCurve("ItemTCRail", "item_rail_tc_parallel_curve_large", 3),

	//TC-JCIR
	remoteController("ItemRemoteController", "remote_controller", 4),
	remoteControllerModule("ItemRemoteControllerModule", "remote_controller_module", 4),
	//signalPairingDevice("ItemSignalPairingDevice", "signal_pairing_device", 14),

	//stEAmeeee//
	minecartClimaxNew("ItemRollingStock", "train_climax2", 5),
	minecartVBShay2("ItemRollingStock", "vbshay2", 5),
	minecartSkook("ItemRollingStock", "skookum", 5),
	minecartSkookTender("ItemRollingStock", "skookum_tender", 4),
	minecartShay3Truck("ItemRollingStock", "3TruckShay", 5),
	minecartShay3TruckTender("ItemRollingStock", "3TruckShayTender", 4),
	minecartF01("ItemRollingStock", "f01", 3),
	minecartP01a("ItemRollingStock", "p01a", 4),
	minecartVanderbackTender("ItemRollingStock", "vanderbacktender", 3),
	minecartBKno2a("ItemRollingStock", "bkno2", 5),
	minecartBKno2b("ItemRollingStock", "bkno2", 5),
	minecartWCPBuckingBull("ItemRollingStock", "wcp_buckingbull", 4),
	minecartWCPBaler("ItemRollingStock", "wcp_baler", 2),

	minecartLocoC11("ItemRollingStock", "train_C11", 5),
	minecartOnion("ItemRollingStock", "Onion", 10),
	minecartOnionTender("ItemRollingStock", "OnionTender", 10),

	minecartPELocomotive("ItemRollingStock", "PELocoIcon", 5),
	minecartPETender("ItemRollingStock", "PETenderIcon", 3),

	//EMD//
	minecartF3A( "ItemRollingStock", "f3a", 4),
	minecartF7A("ItemRollingStock", "f7a", 5),
	minecartF7B("ItemRollingStock", "f7b", 5),
	minecartFP7A("ItemRollingStock", "fp7a", 5),
	minecartFP9A("ItemRollingStock", "fp9a", 5),
	//minecartBUnitF7B("ItemRollingStock", "f7b", 5),
	minecartCF7angle("ItemRollingStock", "cf7angle", 3),
	minecartCF7round("ItemRollingStock", "cf7round", 3),
	minecartCF7angle2("ItemRollingStock", "cf7dbrr", 2),
	minecartF40PH("ItemRollingStock", "f40ph", 5),

	minecartE7A("ItemRollingStock", "e7a",5),
	minecartE7B("ItemRollingStock", "e7b",5),
	//minecartBUnitE7B("ItemRollingStock", "e7b",5),
	minecartE8A( "ItemRollingStock", "e8a", 5),
	minecartE8B( "ItemRollingStock", "e8b", 5),
	//minecartBUnitE8B( "ItemRollingStock", "e8b", 5),
	minecartE9A( "ItemRollingStock", "e9a", 5),
	minecartE9B( "ItemRollingStock", "e9b", 5),
	minecartCE8( "ItemRollingStock", "ce8", 4),

	minecartGP7("ItemRollingStock", "gp7", 3),
	minecartGP7b("ItemRollingStock", "gp7b", 3),
	//minecartBUnitGP7b("ItemRollingStock", "gp7b", 4),
	minecartGP7u("ItemRollingStock", "gp7u", 3),
	minecartGP7f("ItemRollingStock", "gp7f", 3),
	minecartGP9("ItemRollingStock", "gp9", 3),
	minecartGP7old("ItemRollingStock", "gp7old", 3),
	minecartGP9old("ItemRollingStock", "gp9old", 3),
	minecartGP13("ItemRollingStock", "gp13", 3),
	minecartGP15("ItemRollingStock", "gp15", 3),
	minecartGP30("ItemRollingStock", "gp30", 3),
	minecartGP35("ItemRollingStock", "gp35", 3),
	minecartGP38dash2("ItemRollingStock", "gp38dash2", 4),
	minecartGP38dash9W("ItemRollingStock", "gp38dash9w", 3),
	minecartGP38h3("ItemRollingStock", "gp38h3icon", 4),
	minecartGP38("ItemRollingStock", "gp38", 4),
	minecartGP39("ItemRollingStock", "gp39", 4),
	minecartGP40("ItemRollingStock", "gp40", 4),
	minecartGP40tc("ItemRollingStock", "gp40tcicon", 4),
	minecartGP49("ItemRollingStock", "gp49", 5),

	minecartSD9("ItemRollingStock", "sd9", 3),
	minecartSDL39("ItemRollingStock", "SDL39", 4),
	minecartSD38("ItemRollingStock", "sd38", 4),
	minecartSD39("ItemRollingStock", "sd39", 4),
	minecartSD40("ItemRollingStock", "sd40", 4),
	minecartSD40dash2("ItemRollingStock", "sd40dash2", 4),
	minecartSD40T2("ItemRollingStock", "sd40t2", 4),
	minecartSD40dash2B("ItemRollingStock", "sd40dash2b", 4),
	minecartSD40R("ItemRollingStock", "sd40r", 4),
	minecartSD40A("ItemRollingStock", "sd40a", 4),
	minecartSDP40("ItemRollingStock", "sdp40icon", 5),
	minecartSDP40F("ItemRollingStock","sdp40f",5),
	minecartSD45dash2("ItemRollingStock", "sd45", 5),
	minecartSD45dash2B("ItemRollingStock", "sd45dash2b", 5),
	minecartF45("ItemRollingStock", "f45", 5),
	minecartFP45("ItemRollingStock", "fp45", 5),
	minecartSD70Mac("ItemRollingStock", "sd70mac", 6),

	minecartSW1("ItemRollingStock","sw1", 2),
	minecartSW8("ItemRollingStock", "train_sw8", 3),
	minecartSW1000("ItemRollingStock", "sw1000", 3),
	minecartSW1200("ItemRollingStock","sw1200", 4),
	minecartSW1500("ItemRollingStock", "sw1500", 4),
	minecartMP15DCW9("ItemRollingStock", "mp15dcw9", 4),

	minecartBeep("ItemRollingStock", "BeepIcon", 2),

	//GE//
	minecart44Ton("ItemRollingStock", "44-tonner_icon", 2),
	minecartBoxcab23Ton("ItemRollingStock", "boxcab", 2),
	minecart25Ton("ItemRollingStock", "25-tonner_icon", 2),

	minecartU18B("ItemRollingStock", "U18B", 3),
	minecartU18BB("ItemRollingStock", "U18BB", 3),
	minecartU18BW("ItemRollingStock", "U18BW", 3),
	minecartU18Balt("ItemRollingStock", "U18Balt", 3),
	minecartU23B("ItemRollingStock", "u23b", 4),
	minecartU25B("ItemRollingStock", "u25b", 4),
	minecartU30C("ItemRollingStock", "u30c", 5),
	minecartSF30C("ItemRollingStock", "sf30c", 5),
	minecartU36C( "ItemRollingStock", "u36c", 5),

	minecartB23("ItemRollingStock", "b23", 4),
	minecartSB23R("ItemRollingStock", "sb23r", 4),
	minecartSB23E("ItemRollingStock", "sb23e", 4),
	minecartB30("ItemRollingStock", "b30", 4),

	minecartDash840B("ItemRollingStock", "B40-8", 4),
	minecartDash840BB("ItemRollingStock", "B40-8B", 4),
	//minecartBUnitDash840BB("ItemRollingStock", "B40-8B", 4),
	minecartDash840BW("ItemRollingStock", "B40-8W", 4),
	minecartDash840C("ItemRollingStock", "C40-8", 4),

	minecartDash944CW("ItemRollingStock", "d944cw", 5),
	minecartAC4400CW("ItemRollingStock", "ac4400cw", 5),

	minecartES44("ItemRollingStock", "es44", 6),
	minecartP32("ItemRollingStock", "geGenesis2", 4),
	minecartGeGenesis("ItemRollingStock", "geGenesis3", 4),
	minecartP42("ItemRollingStock", "geGenesis", 4),

	//ALCO and its canookistanian counterpart//
	minecartAlcoPA1("ItemRollingStock", "AlcoPA1Icon",5),
	minecartAlcoPB1("ItemRollingStock", "AlcoPB1Icon",4),
	minecartFA1("ItemRollingStock", "FA1",4),
	minecartFB1("ItemRollingStock", "FB1",3),
	minecartFA2("ItemRollingStock", "FA2",4),
	minecartFB2("ItemRollingStock", "FB2",3),
	minecartFPA4("ItemRollingStock", "FPA4",4),
	minecartFPB4("ItemRollingStock", "FPB4",3),
	minecartFAFDL("ItemRollingStock", "FAFDL",4),
	minecartFBFDL("ItemRollingStock", "FBFDL",4),

	minecartC415H("ItemRollingStock", "c415h", 4),
	minecartC415S("ItemRollingStock", "c415s", 4),
	minecartC415L("ItemRollingStock", "c415l", 4),
	minecartC424("ItemRollingStock", "c424", 5),
	minecartC425("ItemRollingStock", "c425", 5),
	minecartDH643("ItemRollingStock", "DH643", 5),
	minecartC855a("ItemRollingStock", "c855a", 6),
	minecartC855b("ItemRollingStock", "c855b", 6),

	minecartM420("ItemRollingStock", "M420", 4),
	minecartM420B("ItemRollingStock", "M420B", 4),

	minecartHH660("ItemRollingStock", "hh660", 2),
	minecartS2("ItemRollingStock", "alco_s2", 2),

	minecartRSD15("ItemRollingStock","RSD15_Icon", 4),

	//BRuhMOMent//
	minecartVO1000("ItemRollingStock", "vo1000", 4),

	//Fairbanks Morse//
	minecartH1044("ItemRollingStock", "h1044", 4),
	minecartH16_66("ItemRollingStock", "H16-66", 5),
	minecartH24_66("ItemRollingStock", "H24-66", 5),
	minecartH24_66L("ItemRollingStock", "H24-66L", 5),

	//other MOtOEr Shuite//
	minecartNRE3gs21b("ItemRollingStock", "3gs21b", 4),
	minecartgtavthing("ItemRollingStock", "gtavthing", 7),
	minecartFOLM1B("ItemRollingStock", "folm1b", 9),
	//minecartBUnitFOLM1B("ItemRollingStock", "folm1b", 4),
	minecartKrautt("ItemRollingStock", "ML4000", 5),
	minecartILMC("ItemRollingStock", "ILMC_ICON", 2),

	minecartSlugMA("ItemRollingStock", "slugma", 2),
	minecartF7BSlug("ItemRollingStock", "f7b", 3),

	//E-lect-trock//
	minecartEF1("ItemRollingStock", "ef1", 6),
	minecartEF1B("ItemRollingStock", "ef1b", 5),
	minecartEP1A("ItemRollingStock", "ep1a", 6),

	minecartGM6C("ItemRollingStock", "GM6C_Icon", 5),

	minecartILMA("ItemRollingStock", "I.L.MA_icon", 2),
	minecartILMB("ItemRollingStock", "I.L.M.B", 2),
	minecartJT7("ItemRollingStock", "jt7", 8),

	minecartAEM7("ItemRollingStock", "aem7", 8),

	//FrERf//
	minecartRoundHopper("ItemRollingStock", "train_RoundHopper", 2),
	minecartRibbedHopper("ItemRollingStock", "ribbed_hopper", 2),
	minecartHopper5201("ItemRollingStock", "Hopper5201", 4),
	minecartHopper6260("ItemRollingStock", "Hopper6260", 5),
	minecartFNCC2375Hopper("ItemRollingStock", "FNCCHopper", 3),
	minecartPDH2800("ItemRollingStock", "pdh2800", 4),

	minecart60centerbeam("ItemRollingStock", "60centerbeam", 3),
	minecart66centerbeam("ItemRollingStock", "66centerbeam", 3),
	minecart73centerbeam("ItemRollingStock", "73centerbeam", 4),
	minecartGSI60FootBulkhead("ItemRollingStock", "gsi_bulky", 4),
	minecartGSC60FootFlatcar("ItemRollingStock", "gsc_flatass", 3),
	minecartthrall63centerbeam("ItemRollingStock", "63centerbeam", 3),
	minecartFNCC60FootBulk("ItemRollingStock", "FNCC60FootBulk", 3),

	minecartPS140("ItemRollingStock", "ps140", 3),
	minecartPS150("ItemRollingStock", "ps150", 4),
	minecartPS160("ItemRollingStock", "ps160", 5),
	minecartOWO60Verticube("ItemRollingStock", "owocube", 6),
	minecartMILW40boxcar("ItemRollingStock", "milw40", 3),
	minecart40highcube("ItemRollingStock", "40highcube", 3),
	minecarthicube60foot("ItemRollingStock", "hicube60", 5),
	minecartReefer64("ItemRollingStock", "Reefer64", 5),
	minecartFNCC50Foot("ItemRollingStock", "FNCC50Foot", 4),
	minecartGN40("ItemRollingStock", "gn40", 3),

	minecartVersaLongi("ItemRollingStock", "versalongi", 4),
	minecartVersaTrans("ItemRollingStock", "versatrans", 4),
	minecartMillGondola("ItemRollingStock", "millgondola", 4),
	minecartWoodchipHopper("ItemRollingStock", "woodchiphopper", 4),
	minecartOreJenny("ItemRollingStock", "orejenny", 2),
	minecartPRRGLaHopper("ItemRollingStock", "PRRGLaHopper", 2),
	minecartACF41Gon("ItemRollingStock", "ACF_41_6_gon", 3),

	minecart11000DOT111("ItemRollingStock", "11000DOT", 4),
	minecart20600DOT111("ItemRollingStock", "20600DOT", 5),
	minecart29080DOT111("ItemRollingStock", "29080DOT", 6),
	minecart17600DOT111("ItemRollingStock", "17600DOT", 4),
	minecartNATX30600("ItemRollingStock", "natx30600", 5),

	minecartSkeletonLogCar("ItemRollingStock", "Skeleton", 1),
	minecartEarlyFlat("ItemRollingStock", "EarlyFlat", 1),

	minecartGunderson89ftAutorack("ItemRollingStock", "gunderson_autorack", 7),
	minecartGunderson89ftFlat("ItemRollingStock", "gunderson_flat", 2),
	minecartStampedeRack("ItemRollingStock", "stampede", 3),

	minecartIngotPig("ItemRollingStock", "pigiron", 3),
	minecartSteelSlabFlat("ItemRollingStock", "steelflat", 3),

	//pAssenger//
	minecartPCH120Commute("ItemRollingStock", "pch120", 4),
	minecartPCH120Car("ItemRollingStock", "pch120car", 3),

	minecartPSRPO("ItemRollingStock", "PSRPOIcon", 3),
	minecartPS85Baggage("ItemRollingStock", "PS_85_baggage_icon", 3),
	minecartPScombine("ItemRollingStock", "PScombine", 3),
	minecartPS52seatCoach("ItemRollingStock", "PS52seatCoach", 3),
	minecartPSDamnitAnotherDiner("ItemRollingStock", "PScenterDiner2", 3),
	minecartPSSleeper565("ItemRollingStock", "PS10-6Sleeper", 3),
	minecartPSSleeper565DRGW("ItemRollingStock", "PS6-5-5Sleeper_icon", 3),
	minecartPSLunchCounter_Lounge("ItemRollingStock", "PSLunchCounter_Lounge", 3),
	minecartPS30SeatParlor("ItemRollingStock", "PS30SeatParlor", 3),

	minecartPSRPOPM("ItemRollingStock", "PSRPOPMIcon", 3),
	minecartPS73Baggage("ItemRollingStock", "PS_73_baggage_icon", 3),
	minecartPS54SeatCoach_Lounge("ItemRollingStock", "PS54SeatCoach_Lounge", 3),
	minecartPS54SeatCoach_Lounge_1950("ItemRollingStock", "PS54SeatCoach_Lounge_1950", 3),
	minecartPSCenterDiner("ItemRollingStock", "PScenterDiner", 3),
	minecartPS58SeatCoach_Observation("ItemRollingStock", "PS58SeatCoach_Observation", 3),

	minecartPSBMCombine("ItemRollingStock", "PSBMCombine", 3),
	minecartPSBM56SeatCoach("ItemRollingStock", "PSBM56SeatCoach", 3),
	minecartPSBMDiner_Lounge("ItemRollingStock", "PSBMDiner_Lounge", 3),

	minecartACFGNRPO_30("ItemRollingStock", "ACFGNRPO_30", 3),
	minecartACFGN60SeatCoach("ItemRollingStock", "ACFGN60SeatCoach", 3),
	minecartACFGNDiner_Observation("ItemRollingStock", "ACFGNDiner_Observation", 3),
	minecartACFGN28SeatCoach_Dinette("ItemRollingStock", "ACFGN28SeatCoach_Dinette", 3),

	minecartOB84SeatCoach("ItemRollingStock", "OB84-92SeatCoach", 3),
	minecartOB92SeatCoach("ItemRollingStock", "OB84-92SeatCoach", 3),

	minecartPSCNW56SeatCoach("ItemRollingStock", "PSCNW56SeatCoach", 3),

	minecartBuddRDG56SeatCoachLounge("ItemRollingStock", "BuddRDG56SeatCoachLounge", 3),

	minecartBuddCRIP52SeatCoach("ItemRollingStock", "BuddCRIP52SeatCoach", 3),

	minecartBuddNYC52SeatCoach("ItemRollingStock", "BuddNYC52SeatCoach", 3),

	minecartBuddCBQ52SeatCoach("ItemRollingStock", "BuddCBQ52SeatCoach", 3),

	minecartBuddATSF52SeatCoach("ItemRollingStock", "BuddATSF52SeatCoach", 3),
	minecartBuddATSF48SeatCoach("ItemRollingStock", "BuddATSF48SeatCoach", 3),

	minecartAmfleet("ItemRollingStock", "Amfleet", 3),
	minecartAmfleet2("ItemRollingStock", "Amfleet2", 3),
	minecartAmfleetCab("ItemRollingStock", "AmfleetCab", 3),
	minecartAmCafe("ItemRollingStock", "AmCafe", 3),
	minecartAmCafe2("ItemRollingStock", "AmCafe2", 3),

	minecartPEcooch("ItemRollingStock", "pecoach", 6),
	minecartPEobserve("ItemRollingStock", "peobserve", 6),

	//othershizzle//
	minecartWVcaboose("ItemRollingStock", "wvcaboose",3),
	minecartHBC1Ccaboose("ItemRollingStock", "HBC1C",3),
	minecartDRGWCaboose("ItemRollingStock", "DRGW_01400_series_caboose",3),

	minecartBigMeme("ItemRollingStock", "lol", 69),
	minecartBombCart("ItemRollingStock", "payload", 2),
	minecartThanos("ItemRollingStock", "thanos", 64),
	minecartTGVMobile("ItemRollingStock", "tgvmobile", 30),
	minecartHHgregg("ItemRollingStock", "hhgregg", 69),

	minecartCQ310PO("ItemRollingStock", "CQ_310_Loco_icon",3),
	minecartCQ310PA("ItemRollingStock", "CQ_310_Tail_icon",3),
	minecartKawasakiLRV("ItemRollingStock", "KawasakiLRV",3),

	//peach moments
	minecartGeometryCar("ItemRollingStock", "geometry_car", 15),
	minecartExperimentalHydrogenTrain("ItemRollingStock", "pch100h", 21),
	minecartPCH100HCoach("ItemRollingStock", "pch100h_coach", 15),
	//testControlCar("ItemRollingStock", "test_controlcar", 100000),
	minecartAipkitExplorer("ItemRollingStock", "explorer", 14),
	minecartAipkitExplorer2("ItemRollingStock", "explorer2", 15),

	//ah, but we, are british
	//minecartLUengine("ItemRollingStock","luengine", 3),
	//minecartLUcar("ItemRollingStock","lupassenger", 2),
	//minecartClass345engine("ItemRollingStock", "Class345Engine", 3),
	//minecartClass345car("ItemRollingStock", "class345coach", 2),


	//undecided stuff//
	//minecartBaldwinMineLoco("ItemRollingStock", "baldwinmineloco", 1),
	minecartHighrailTruck("ItemRollingStock", "highrail", 3),
	minecartFRED("ItemRollingStock", "fred", 5),

	//minecartHuskyStackWellcar("ItemRollingStock", "huskystack", 3),
	minecartHustler("ItemRollingStock", "hustler", 2),
	minecartBoulderWagon("ItemRollingStock","BoulderWagon",3),
	minecartLogcarNP( "ItemRollingStock","logcarNP",3),
	//minecartCF7R("ItemRollingStock", "cf7r", 12),
	minecartHighrailVan("ItemRollingStock", "highrailvan", 3),

	;

	public Item item;
	public String className;
	public String iconName;

	/**
	 * amount for one emerald. For ItemRollingStock, it is the price for one train
	 */
	public int amountForEmerald;

	/**
	 * @param classMethodName
	 * @param iconName
	 * @param amountForEmerald for one emerald. For ItemRollingStock, it is the price for one train
	 */
	ItemIDs(String classMethodName, String iconName, int amountForEmerald) {
		this.className = classMethodName;
		this.iconName = iconName;
		this.amountForEmerald = amountForEmerald;
	}

}