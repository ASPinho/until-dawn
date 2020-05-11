package deltaqueues.dronnie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import deltaqueues.dronnie.streetGame.PlataformPlayer;
import deltaqueues.dronnie.tileObjects.Enemy;
import deltaqueues.dronnie.tileObjects.EnemyShot;
import deltaqueues.dronnie.tileObjects.InteractiveTileObject;


import static deltaqueues.dronnie.Utilities.*;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();

        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;


        switch (cDef) {

            case PLAYER_FIRE_BIT | ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == ENEMY_BIT) {

                    ((Enemy) fixA.getUserData()).destroy();
                } else {

                    ((Enemy) fixA.getUserData()).destroy();
                }
                break;
        }


        if (fixA.getUserData() == null || fixB.getUserData() == null) {
            return;
        }
        if (fixA.getUserData().equals("ladder") || fixB.getUserData().equals("ladder")) {
            Fixture bottom = fixA.getUserData().equals("ladder") ? fixA : fixB;
            Fixture object = bottom == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

/*
        if(fixA.getUserData().equals("bottom") || fixB.getUserData().equals("bottom")) {
            Fixture bottom = fixA.getUserData().equals("bottom") ? fixA : fixB;
            Fixture object = bottom == fixA ? fixB : fixA;

            if(object.getUserData() != null && deltaqueues.dronnie.tileObjects.InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((deltaqueues.dronnie.tileObjects.InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }*/


    }

    @Override
    public void endContact(Contact contact) {

        short firstBit = contact.getFixtureA().getFilterData().categoryBits;
        short secondBit = contact.getFixtureB().getFilterData().categoryBits;


        if ((firstBit | secondBit) == (PLAYER_BIT | LADDER_BIT)) {


            if (contact.getFixtureA().getUserData() == (null) || contact.getFixtureB().getUserData() == (null)) {
                return;
            }

            Fixture ladder = contact.getFixtureA().getUserData().equals("ladder") ? contact.getFixtureA() : contact.getFixtureB();
            Fixture object = ladder == contact.getFixtureA() ? contact.getFixtureB() : contact.getFixtureA();

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {

                ((InteractiveTileObject) object.getUserData()).afterHit();
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

        short firstBit = contact.getFixtureA().getFilterData().categoryBits;
        short secondBit = contact.getFixtureB().getFilterData().categoryBits;


        if ((firstBit | secondBit) == (PLAYER_BIT | LADDER_BIT)) {

            contact.setEnabled(false);
        }

        Fixture fixA = contact.getFixtureA();

        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;


        switch (cDef) {

            case PLAYER_FIRE_BIT | ENEMY_BIT:

                if (fixA.getFilterData().categoryBits == ENEMY_BIT) {



                    if (((Enemy) fixA.getUserData()).getDestroyed()) {

                        contact.setEnabled(false);
                    }
                } else if (fixB.getFilterData().categoryBits == ENEMY_BIT) {



                    if (((Enemy) fixB.getUserData()).getDestroyed()) {

                        contact.setEnabled(false);
                    }

                }


            case ENEMY_FIRE_BIT | PLAYER_BIT:

                if (fixA.getFilterData().categoryBits == ENEMY_FIRE_BIT) {

                    ((PlataformPlayer) fixB.getUserData()).takeDamage();
                    contact.setEnabled(false);

                } else if (fixB.getFilterData().categoryBits == ENEMY_FIRE_BIT) {

                    System.out.println(fixA.getUserData());
                    if(fixA.getUserData() != null && PlataformPlayer.class.isAssignableFrom(fixA.getUserData().getClass())) {
                        ((PlataformPlayer) fixA.getUserData()).takeDamage();
                    }
                    contact.setEnabled(false);
                }
        }
    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
