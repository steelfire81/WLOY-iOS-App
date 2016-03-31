//
//  FirstViewController.swift
//  WLOY App
//
//  Created by William Robbins on 3/15/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import UIKit
import AVFoundation

class NowPlayingViewController: UIViewController {

    // CONSTANTS
    let DEFAULT_ARTIST = "Artist Name"
    let DEFAULT_SHOW = "Show Name"
    let DEFAULT_SONG = "Song Name"
    let FEED_ADDRESS = "http://wloy.radioactivity.fm/feeds/last10.xml"
    let UPDATE_INTERVAL = 5 // duration of XML update delay, in seconds
    let STREAM_ADDRESS = "http://war.str3am.com:8130/live"
    
    // DATA MEMBERS
    var audioPlayer: AVPlayer!
    var xmlParser: NSXMLParser!
    var xmlParserDelegate: WLOYSongParserDelegate!
    
    // OUTLETS
    @IBOutlet weak var showNameLabel: UILabel!
    @IBOutlet weak var volume: UISlider!
    @IBOutlet weak var nowPlayingLabel: UILabel!
    
    // METHODS
    override func viewDidLoad() {
        super.viewDidLoad()
        loadAudioPlayer()
        setNowPlayingLabel(DEFAULT_ARTIST, song:DEFAULT_SONG)
        setCurrentShowLabel(DEFAULT_SHOW)
        
        // Initialize XML parser
        xmlParser = NSXMLParser.init(contentsOfURL:NSURL.init(string: FEED_ADDRESS)!)
        xmlParserDelegate = WLOYSongParserDelegate.init()
        xmlParser.delegate = xmlParserDelegate
        checkXML()
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // setCurrentShowLabel - update text on the current show field
    func setCurrentShowLabel(show: String) {
        showNameLabel.text = show
    }
    
    // setNowPlayingLabel - given an artist and song, update the now playing field
    func setNowPlayingLabel(artist: String, song: String) {
        nowPlayingLabel.text = artist + " - " + song
    }
    
    // startTimerThread - create a thread that starts listening for XML updates
    func startTimerThread() {
        // TODO: Actually get this to work
        NSLog("Timer thread started! (not really)")
        checkXML()
    }
    
    // checkXML - checks XML file and updates fields to appropriate values
    func checkXML() {
        xmlParser.parse()
        setNowPlayingLabel(xmlParserDelegate.currentArtist, song:xmlParserDelegate.currentSong)
    }
    
    // loadAudioPlayer - (re)loads the audio stream into audioPlayer
    func loadAudioPlayer() {
        audioPlayer = AVPlayer.init(URL:NSURL.init(string:STREAM_ADDRESS)!)
        audioPlayer.rate = 1.0
        audioPlayer.volume = volume.value // TODO: Adjust volume as slider changes
        audioPlayer.play()
    }

}

