package com.luzi82.shinju;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.TimeUtils;
import com.luzi82.shinju.logic.BulletSimple;
import com.luzi82.shinju.logic.Element;
import com.luzi82.shinju.logic.Hero;
import com.luzi82.shinju.logic.Skill;
import com.luzi82.shinju.logic.Unit;
import com.luzi82.shinju.logic.Witch;
import com.luzi82.shinju.logic.World;

public class ShinjuGame extends Game {

	ShinjuCommon common;
	Screen currentScreen;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Unit.initBlock();
		common = new ShinjuCommon();

		common.mShinjuData = new World();
		common.mShinjuActTurn = 0;
		common.mShinjuActTime = TimeUtils.millis();

		Hero hero;
		Witch witch;
		BulletSimple.Ski bulletSimple;

		hero = Hero.create(common.mShinjuData);
		hero.position.get().x.set(4L * ShinjuCommon.CELL_SIZE);
		hero.position.get().y.set(4L * ShinjuCommon.CELL_SIZE);
		hero.hp.get().value.set(100L);
		hero.hp.get().max.set(100L);
		hero.mp.get().value.set(100L);
		hero.mp.get().max.set(100L);
		hero.mp.get().min.set(Long.MIN_VALUE);

		bulletSimple = BulletSimple.Ski.create(hero);
		bulletSimple.cooldown.set(10L);
		bulletSimple.mp_cost.set(10L);
		bulletSimple.damage.set(10L);
		bulletSimple.range.set(5L * ShinjuCommon.CELL_SIZE);
		bulletSimple.speed.set(ShinjuCommon.CELL_SIZE);
		bulletSimple.target_unit_type_list.get().add(Witch.TYPE);

		hero = Hero.create(common.mShinjuData);
		hero.position.get().x.set(10L * ShinjuCommon.CELL_SIZE);
		hero.position.get().y.set(10L * ShinjuCommon.CELL_SIZE);
		hero.hp.get().value.set(100L);
		hero.hp.get().max.set(100L);
		hero.mp.get().value.set(100L);
		hero.mp.get().max.set(100L);
		hero.mp.get().min.set(Long.MIN_VALUE);

		bulletSimple = BulletSimple.Ski.create(hero);
		bulletSimple.cooldown.set(10L);
		bulletSimple.mp_cost.set(10L);
		bulletSimple.damage.set(10L);
		bulletSimple.range.set(5L * ShinjuCommon.CELL_SIZE);
		bulletSimple.speed.set(ShinjuCommon.CELL_SIZE);
		bulletSimple.target_unit_type_list.get().add(Witch.TYPE);

		witch = Witch.create(common.mShinjuData);
		witch.position.get().x.set(6L * ShinjuCommon.CELL_SIZE);
		witch.position.get().y.set(6L * ShinjuCommon.CELL_SIZE);
		witch.hp.get().value.set(100L);
		witch.hp.get().max.set(100L);

		setScreen(new PlayScreen(common));
	}
}
