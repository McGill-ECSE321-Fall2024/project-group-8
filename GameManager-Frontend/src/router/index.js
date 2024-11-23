import { createRouter, createWebHistory } from 'vue-router';
import GameList from '../components/GameList.vue';
import GameDetails from '../components/GameDetails.vue';
import CreateGame from '../components/CreateGame.vue';
import UpdateGame from '../components/UpdateGame.vue';
import Login from '@/components/Login.vue';
import Cart from "@/components/Cart.vue";

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
    path: '/cart',
    name: 'Cart',
    component: Cart,
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/',
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/',
    redirect: '/login',
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
