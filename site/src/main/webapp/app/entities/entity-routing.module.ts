import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'style',
        data: { pageTitle: 'learnjhipsterApp.style.home.title' },
        loadChildren: () => import('./style/style.module').then(m => m.StyleModule),
      },
      {
        path: 'artist',
        data: { pageTitle: 'learnjhipsterApp.artist.home.title' },
        loadChildren: () => import('./artist/artist.module').then(m => m.ArtistModule),
      },
      {
        path: 'album',
        data: { pageTitle: 'learnjhipsterApp.album.home.title' },
        loadChildren: () => import('./album/album.module').then(m => m.AlbumModule),
      },
      {
        path: 'song',
        data: { pageTitle: 'learnjhipsterApp.song.home.title' },
        loadChildren: () => import('./song/song.module').then(m => m.SongModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
