package com.kreitek.jhipster.service.impl;

import com.kreitek.jhipster.service.*;
import com.kreitek.jhipster.service.criteria.AlbumCriteria;
import com.kreitek.jhipster.service.criteria.ArtistCriteria;
import com.kreitek.jhipster.service.dto.AlbumDTO;
import com.kreitek.jhipster.service.dto.AlbumFacadeDTO;
import com.kreitek.jhipster.service.dto.ArtistDTO;
import com.kreitek.jhipster.service.dto.SongDTO;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AlbumFacadeServiceImpl implements AlbumFacadeService {

    private final AlbumService albumService;

    private final AlbumQueryService albumQueryService;

    private final ArtistService artistService;

    private final ArtistQueryService artistQueryService;

    private final SongService songService;

    public AlbumFacadeServiceImpl(AlbumService albumService, AlbumQueryService albumQueryService, ArtistService artistService, ArtistQueryService artistQueryService, SongService songService) {
        this.albumService = albumService;
        this.albumQueryService = albumQueryService;
        this.artistService = artistService;
        this.artistQueryService = artistQueryService;
        this.songService = songService;
    }

    @Override
    @Transactional
    public AlbumFacadeDTO createAlbum(AlbumFacadeDTO albumFacadeDTO) {
        AlbumFacadeDTO result = new AlbumFacadeDTO();
        boolean albumExists = false;

        /*Verificar que el artista existe*/
        ArtistCriteria artistCriteria = new ArtistCriteria();
        StringFilter artistName = new StringFilter();
        artistName.setEquals(albumFacadeDTO.getArtist().getName());
        // el campo nombre es único en la bd, por lo que no pueden haber 2 artistas con el mismo nombre (al menos en esta bd ficticia)
        List<ArtistDTO> artistDTOS = artistQueryService.findByCriteria(artistCriteria);
            if (artistDTOS.size() > 0) {
                result.setArtist(artistDTOS.get(0));
            } else {
                result.setArtist(artistService.save(albumFacadeDTO.getArtist()));
            }


        /*Verificar si existe algún album de ese artista con ese nombre*/
        AlbumCriteria albumCriteria = new AlbumCriteria();
        LongFilter artistId = new LongFilter();
        artistId.setEquals(result.getArtist().getId());
        StringFilter albumtitle = new StringFilter();
        albumtitle.setEquals(albumFacadeDTO.getTitle());
        List<AlbumDTO> albumDTOS = albumQueryService.findByCriteria(albumCriteria);
            if (albumDTOS.size() > 0) {
                albumExists = true;
            } else {
                // crear albúm

                //verificar si existen las canciones
                Set<SongDTO> songResult = new HashSet<>();
                albumFacadeDTO.getSongs().forEach(eachSong -> {

                    Optional<SongDTO> optionalSongDTO = songService.findOne(eachSong.getId());

                    if (optionalSongDTO.isEmpty()) {
                        songResult.add(songService.save(eachSong));
                    } else {
//                        songResult.add(songService)
                    }
                });

            }

        return result;
    }
}
