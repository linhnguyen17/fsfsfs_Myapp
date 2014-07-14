package com.example.myapp;


import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;


public class SplashAndengine extends SimpleBaseGameActivity  {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	protected static final int CONTINUE = 0;
	protected static final int NEWGAME = CONTINUE + 1;
	protected static final int MAINMENU = NEWGAME + 1;
	protected static final int MENU_QUIT = MAINMENU + 1;
	public static boolean SOUND = true;//Ban đầu âm thanh luôn được bật
	private BoundCamera camera ;
	private Scene scene;
	protected MenuScene mMenuScene;
	// font
	private static  AutoParallaxBackground autoParallaxBackground;

	// ===========================================================
	// Fields
	// ===========================================================

	private BitmapTextureAtlas mAutoParallaxBackgroundTexture;

	private TextureRegion mParallaxLayerBack;
	private TextureRegion mParallaxLayerMid;

	// ===========================================================
	// Constructors
	// ===========================================================
	// ===========================================================
	// Control
	// ===========================================================
	// ===========================================================
	// Object
	// ===========================================================
	//Text
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {

		this.camera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		EngineOptions en =  new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
		en.getAudioOptions().setNeedsSound(true);
		en.getAudioOptions().setNeedsMusic(true);

		// enabling MultiTouch if available
//		if(MultiTouch.isSupported(this)) {
//			if(MultiTouch.isSupportedDistinct(this)) {
//				Toast.makeText(this, "MultiTouch detected --> Both controls will work properly!", Toast.LENGTH_SHORT).show();
//			} else {
//				Toast.makeText(this, "MultiTouch detected, but your device has problems distinguishing between fingers.\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
//			}
//		} else {
//			Toast.makeText(this, "Sorry your device does NOT support MultiTouch!\n\n(Falling back to SingleTouch.)\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
//		}
		return en;
	}
	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024	, 1024);
		this.mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "bg1.png", 0, 0);
		this.mParallaxLayerMid = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "bg3.png", 0, 480);
		this.mAutoParallaxBackgroundTexture.load();
		

	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		scene = new Scene();
//		scene.setBackground( new Background(0.09804f,0.6274f,0.8784f));
		
		autoParallaxBackground = new AutoParallaxBackground(0.333f, 0.133f, 0.055f, 5);
		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-2.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerBack.getHeight(), this.mParallaxLayerBack, vertexBufferObjectManager)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(4.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerMid.getHeight(), this.mParallaxLayerMid, vertexBufferObjectManager)));
		scene.setBackground(autoParallaxBackground);

		return scene;
	}


}
