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
    let DEFAULT_DJ = "DJ Name"
    let IMAGE_ANIM_DURATION = 10.0
    let SONG_FEED_ADDRESS = "http://wloy.radioactivity.fm/feeds/last10.xml"
    let UPDATE_INTERVAL = 5.0 // length between XML updates in seconds
    let SHOW_FEED_ADDRESS = "http://wloy.radioactivity.fm/feeds/showonair.xml"
    let STREAM_ADDRESS = "http://war.str3am.com:8130/live"
    let WLOY_LOGO_ADDRESS = "wloy-logo.gif"
    
    // DATA MEMBERS
    var audioPlayer: AVPlayer!
    var songXMLParser: NSXMLParser!
    var songXMLParserDelegate: WLOYSongParserDelegate!
    var showXMLParser: NSXMLParser!
    var showXMLParserDelegate: WLOYShowParserDelegate!
    var currentSongTitle:String!
    var currentArtist:String!
    var currentShow:String!
    var currentDJ:String!
    var xmlUpdateTimer:NSTimer!
    
    // OUTLETS
    @IBOutlet weak var showNameLabel: UILabel!
    @IBOutlet weak var volume: UISlider!
    @IBOutlet weak var nowPlayingLabel: UILabel!
    @IBOutlet weak var currentDJLabel: UILabel!
    @IBOutlet weak var currentArtistLabel: UILabel!
    @IBOutlet weak var imageView: UIImageView!
    
    // STORYBOARD ACTIONS
    // volumeSliderMoved - called whenever the volume slider changes
    @IBAction func volumeSliderMoved(sender: AnyObject) {
        audioPlayer.volume = volume.value
    }
    
    // METHODS
    override func viewDidLoad() {
        super.viewDidLoad()
        loadAudioPlayer()
        setNowPlayingLabel(DEFAULT_ARTIST, song:DEFAULT_SONG)
        setCurrentShowLabel(DEFAULT_SHOW, dj:DEFAULT_DJ)
        
        // Initialize Song XML Parser
        songXMLParser = NSXMLParser(contentsOfURL:NSURL(string: SONG_FEED_ADDRESS)!)
        songXMLParserDelegate = WLOYSongParserDelegate()
        songXMLParser.delegate = songXMLParserDelegate
        checkSongXML()
        
        // Initialize Show XML Parser
        showXMLParser = NSXMLParser(contentsOfURL:NSURL(string: SHOW_FEED_ADDRESS)!)
        showXMLParserDelegate = WLOYShowParserDelegate()
        showXMLParser.delegate = showXMLParserDelegate
        checkShowXML()
        
        // Initialize image view
        updateImageArray([UIImage]())
        
        // Initialize backend connector
        BackendConnector.generateID()
        BackendConnector.sendConnectionMessage()
        BackendConnector.startTimer()
        
        // Start update timer
        startTimer()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // setCurrentShowLabel - update text on the current show field
    func setCurrentShowLabel(show: String, dj: String) {
        currentShow = show
        currentDJ = dj
        
        if(show == "") {
            showNameLabel.text = dj
        }
        else {
            showNameLabel.text = show + " - " + dj
        }
    }
    
    // setNowPlayingLabel - given an artist and song, update the now playing field
    func setNowPlayingLabel(artist: String, song: String) {
        currentArtist = artist
        currentSongTitle = song
        
        nowPlayingLabel.text = artist + " - " + song
    }
    
    // startTimer - create a thread that starts listening for XML updates
    func startTimer() {
        xmlUpdateTimer = NSTimer.scheduledTimerWithTimeInterval(UPDATE_INTERVAL, target:self, selector:"timerFired", userInfo:nil, repeats:true)
    }
    
    // timerFired - check XML whenever timer fires
    func timerFired() {
        checkSongXML()
        checkShowXML()
    }
    
    // checkXML - checks XML file and updates fields to appropriate values
    func checkSongXML() {
        songXMLParser.parse()
        setNowPlayingLabel(songXMLParserDelegate.currentArtist, song:songXMLParserDelegate.currentSong)
    }
    
    // checkShowXML - checks show XML file and updates fields to appropriate values
    func checkShowXML() {
        showXMLParser.parse()
        setCurrentShowLabel(showXMLParserDelegate.currentShowName, dj:showXMLParserDelegate.currentDJ)
    }
    
    // loadAudioPlayer - (re)loads the audio stream into audioPlayer
    func loadAudioPlayer() {
        audioPlayer = AVPlayer(URL:NSURL(string:STREAM_ADDRESS)!)
        audioPlayer.rate = 1.0
        audioPlayer.volume = volume.value
        audioPlayer.play()
    }
    
    // sendFeedback - send like/dislike feedback to the backend server
    func sendFeedback(positive:Bool) {
        BackendConnector.sendFeedbackMessage(positive, songTitle:currentSongTitle, artist:currentArtist, show:currentShow, dj:currentDJ)
    }
    
    // sendPositiveFeedback - called when "Like" button is pressed
    @IBAction func sendPositiveFeedback(sender: AnyObject) {
        sendFeedback(true)
    }
    
    // sendNegativeFeedback - called when "Dislike" button is pressed
    @IBAction func sendNegativeFeedback(sender: AnyObject) {
        sendFeedback(false)
    }
    
    // updateImageArray - set the image view to alternate between a new array of images
    func updateImageArray(imageArray:[UIImage]) {
        imageView.stopAnimating()
        
        // Add wloy logo at the end
        var updatedImageArray = imageArray
        updatedImageArray.append(UIImage(named:WLOY_LOGO_ADDRESS)!)
        
        imageView.animationImages = updatedImageArray
        imageView.animationDuration = IMAGE_ANIM_DURATION
        imageView.startAnimating()
    }
    
}

