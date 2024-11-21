import { createRouter, createWebHistory } from 'vue-router';
import GameList from '../components/GameList.vue';
import GameDetails from '../components/GameDetails.vue';
import CreateGame from '../components/CreateGame.vue';
import UpdateGame from '../components/UpdateGame.vue';

const routes = [
  {
    path: '/',
    name: 'GameList',
    component: GameList,
  },
  {
    path: '/games/:id',
    name: 'GameDetails',
    component: GameDetails,
    props: true,
  },
  {
    path: '/create-game',
    name: 'CreateGame',
    component: CreateGame,
  },
  {
    path: '/update-game/:id',
    name: 'UpdateGame',
    component: UpdateGame,
    props: true,
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/',
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
