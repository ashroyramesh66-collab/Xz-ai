package com.example.data

data class AppPreset(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconName: String,
    val category: String,
    val trendingIdeas: List<String>,
    val defaultFeatures: List<String>,
    val defaultTheme: String,
    val defaultFont: String,
    val defaultArchitecture: String
)

data class ThemeOption(
    val id: String,
    val name: String,
    val description: String,
    val primaryColorHex: String,
    val surfaceColorHex: String,
    val accentColorHex: String
)

data class FontPairOption(
    val id: String,
    val headingFont: String,
    val bodyFont: String,
    val googleFontLink: String
)

object AppPresetData {

    val PRESETS = listOf(
        AppPreset(
            id = "ff_tournament",
            title = "Free Fire Tournament App",
            subtitle = "Admin match host + User slot booking & room ID display",
            iconName = "sports_esports",
            category = "Gaming / Tournaments",
            trendingIdeas = listOf(
                "Daily Paid/Free Solo, Duo & Squad FF Matches with instant slot registration",
                "Per-Kill Bounty & Chicken Dinner automated prize calculations",
                "Secret Room ID & Password countdown release for booked players only",
                "Proof Screenshot upload link submission & manual admin wallet approval",
                "Player Leaderboard with top earnings & kill statistics"
            ),
            defaultFeatures = listOf(
                "Top App Bar with live user wallet balance & notification bell",
                "Banner Slider for featured mega tournaments & rules",
                "Match Cards with status pills (Upcoming, Ongoing, Full, Completed)",
                "Match Details Modal: Entry Fee, Prize Pool, Per Kill, Map, Time",
                "Slot Booking system (User enters FF Game UID & Nickname)",
                "Locked Room ID & Password screen (Unlocks 15 mins before match)",
                "Wallet Screen: Add Money instructions, UPI Transaction ID submit, Transaction History",
                "Admin Panel: Create new tournament with direct banner URL & prize details",
                "Admin Panel: Update Room ID and Password before match begins",
                "Admin Panel: Verify player slot bookings and declare match results",
                "Admin Panel: Manage user wallet balances & approve manual top-ups",
                "Native Bottom Navigation (Home, My Matches, Wallet, Profile)",
                "Pure Firebase Realtime Database sync (Zero Firebase Storage)",
                "External Image URL direct preview (ImgBB / PostImages)"
            ),
            defaultTheme = "Cyber AMOLED Gaming (Dark + Neon Cyan + Fiery Orange)",
            defaultFont = "Rajdhani (Headers) + Inter (Body)",
            defaultArchitecture = "Admin + User Panel (2 Single HTML Files + Firebase RTDB)"
        ),
        AppPreset(
            id = "wallpaper_hub",
            title = "HD Wallpaper App",
            subtitle = "Admin image link manager + User gallery with 1-tap download",
            iconName = "wallpaper",
            category = "Media & Utilities",
            trendingIdeas = listOf(
                "Trending 4K AMOLED, Anime, Minimalist & Superhero categories",
                "1-tap Direct Download / Set as Wallpaper web simulator",
                "Favorites / Bookmarks saved locally or in user profile",
                "Admin direct image upload via free image hosting links (ImgBB/Imgur)",
                "Dark/Light dynamic toggle with fluid waterfall grid"
            ),
            defaultFeatures = listOf(
                "Staggered Masonry Waterfall Grid with lazy-loaded wallpaper cards",
                "Category Pill Carousel (Anime, AMOLED, Nature, Gaming, Minimal, Abstract)",
                "Full-Screen Wallpaper Viewer modal with zoom and high-res preview",
                "1-tap Download button (direct trigger using HTML5 download attribute)",
                "Like / Favorite counter synced with Firebase Realtime Database",
                "Search bar with live instant client-side filtering",
                "Admin Panel: Add new wallpaper with Title, Category, and Direct Image URL",
                "Admin Panel: Manage existing wallpapers (Edit title, Delete, Pin to Featured)",
                "Zero Firebase Storage requirement (all images hosted on free image hosts)",
                "Native Android bottom navigation (Explore, Categories, Favorites, About)"
            ),
            defaultTheme = "Ultra Minimal Obsidian (Deep Dark + Emerald Glow)",
            defaultFont = "Outfit (Display) + Plus Jakarta Sans (Body)",
            defaultArchitecture = "Admin + User Panel (2 Single HTML Files + Firebase RTDB)"
        ),
        AppPreset(
            id = "ecommerce_store",
            title = "E-Commerce Mini Store",
            subtitle = "Admin product manager + User catalog, cart & WhatsApp checkout",
            iconName = "shopping_bag",
            category = "Shopping & Commerce",
            trendingIdeas = listOf(
                "Direct WhatsApp Order placement with pre-formatted invoice message",
                "Flash Sale countdown banner with discounted price tags",
                "Multi-category product filter & stock availability indicators",
                "Admin instant price & stock editor via Firebase Realtime Database",
                "Order history saved locally on user device"
            ),
            defaultFeatures = listOf(
                "Sticky Header with Search, Category Filter, and floating Cart Badge",
                "Hero Promotional Carousel with auto-rotating discount banners",
                "Product Cards with Direct Image URL, Old Price, Sale Price, and 'Add' button",
                "Product Detail Modal with image gallery preview and feature bullet points",
                "Interactive Slide-up Cart Drawer with Quantity Increment/Decrement and Subtotal",
                "1-tap WhatsApp Checkout: Formats complete order details with address & item names",
                "Admin Panel: Add Product (Title, Direct Image URL, Price, Stock, Category)",
                "Admin Panel: Live inventory editor & Flash Sale toggle",
                "Admin Panel: View received order logs from Firebase Realtime Database",
                "Native Material 3 card ripples and snackbar toast on item added"
            ),
            defaultTheme = "Material You Vibrant (Deep Indigo + Electric Amber)",
            defaultFont = "Poppins (Headers) + Inter (Body)",
            defaultArchitecture = "Admin + User Panel (2 Single HTML Files + Firebase RTDB)"
        ),
        AppPreset(
            id = "multiplayer_game",
            title = "Realtime Multiplayer Game",
            subtitle = "Ludo / Tic-Tac-Toe / Quiz Battle with Firebase Room codes",
            iconName = "videogame_asset",
            category = "Multiplayer Gaming",
            trendingIdeas = listOf(
                "Realtime Room Code creation & 6-digit Join Game matchmaking",
                "Live synchronized turns & game board state via Firebase RTDB listeners",
                "In-game sound effects & celebratory victory confetti animation",
                "Player ready status, avatar selection, and live chat emojis",
                "Match history & player win-streak scoreboard"
            ),
            defaultFeatures = listOf(
                "Game Lobby: 'Create Room' generates unique 6-character room code",
                "Join Room Screen: Enter 6-digit room code to connect with friend",
                "Realtime Waiting Room: Shows Player 1 (Host) and Player 2 (Challenger) status",
                "Live Game Board: State syncs instantly through Firebase RTDB `ref.on('value')`",
                "Turn indicator with timer countdown and active player highlights",
                "Win/Loss/Draw detection with confetti animation and game reset handler",
                "Firebase Realtime Database setup instructions & security rules included in code",
                "Fully single-file HTML structure with audio synthesis & canvas effects"
            ),
            defaultTheme = "Neon Cyberpunk Arcade (Dark Violet + Cyan & Hot Pink)",
            defaultFont = "Space Grotesk (Headers) + DM Sans (Body)",
            defaultArchitecture = "Online Multiplayer Game (1 Single HTML File + Firebase RTDB)"
        ),
        AppPreset(
            id = "task_rewards",
            title = "Task & Spin Reward App",
            subtitle = "User daily check-in, lucky spin & admin task coin manager",
            iconName = "monetization_on",
            category = "Rewards & Engagement",
            trendingIdeas = listOf(
                "Daily Streak Bonus & Canvas-based Lucky Spin Wheel",
                "Offerwall / Task list (Watch YouTube video link, Visit website)",
                "Coin to Cash redemption requests with Paytm / UPI / PayPal fields",
                "Admin withdrawal request approvals & task updater"
            ),
            defaultFeatures = listOf(
                "Dashboard with Coin Balance Card and Daily Streak Check-in",
                "Interactive Canvas Lucky Wheel with rotation physics and sound effects",
                "Task Cards with reward coins, description, and direct link click verification",
                "Redeem Store: Convert coins to real vouchers / UPI transfers with withdrawal form",
                "Admin Panel: View pending withdrawal requests and mark as 'Paid'",
                "Admin Panel: Add new reward tasks with coin amount and target URL",
                "Firebase Realtime Database for all coin balances and withdrawal queues",
                "Strict native Android bottom navigation and feedback haptics"
            ),
            defaultTheme = "Gold & Royal Slate (Charcoal + Radiant Gold Accent)",
            defaultFont = "Montserrat (Headers) + Roboto (Body)",
            defaultArchitecture = "Admin + User Panel (2 Single HTML Files + Firebase RTDB)"
        )
    )

    val THEMES = listOf(
        ThemeOption(
            id = "amoled_cyber",
            name = "Cyber AMOLED Neon",
            description = "Pitch black AMOLED #0A0A0E with Electric Cyan & Neon Violet accents. Perfect for gaming & tournament apps.",
            primaryColorHex = "#00E5FF",
            surfaceColorHex = "#12141C",
            accentColorHex = "#A855F7"
        ),
        ThemeOption(
            id = "material_you",
            name = "Material 3 Expressive",
            description = "Google Material 3 design with vibrant primary indigo #6366F1, tonal surfaces, and pill indicators.",
            primaryColorHex = "#6366F1",
            surfaceColorHex = "#1E1E2E",
            accentColorHex = "#38BDF8"
        ),
        ThemeOption(
            id = "luxe_gold",
            name = "Luxury Midnight & Gold",
            description = "Rich charcoal black with champagne gold #F59E0B accents. Premium feel for rewards and e-commerce.",
            primaryColorHex = "#F59E0B",
            surfaceColorHex = "#18181B",
            accentColorHex = "#10B981"
        ),
        ThemeOption(
            id = "frosted_glass",
            name = "Modern Glassmorphism",
            description = "Translucent frosted acrylic cards with backdrop-blur, subtle 1px border glows, and vivid gradients.",
            primaryColorHex = "#EC4899",
            surfaceColorHex = "rgba(30, 41, 59, 0.7)",
            accentColorHex = "#06B6D4"
        ),
        ThemeOption(
            id = "clean_emerald",
            name = "Clean Emerald & Dark Slate",
            description = "Sophisticated dark slate background with refreshing emerald green #10B981 highlights. Clean and professional.",
            primaryColorHex = "#10B981",
            surfaceColorHex = "#0F172A",
            accentColorHex = "#3B82F6"
        )
    )

    val FONT_PAIRS = listOf(
        FontPairOption(
            id = "poppins_inter",
            headingFont = "Poppins",
            bodyFont = "Inter",
            googleFontLink = "https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Poppins:wght@600;700;800&display=swap"
        ),
        FontPairOption(
            id = "rajdhani_inter",
            headingFont = "Rajdhani",
            bodyFont = "Inter",
            googleFontLink = "https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&family=Rajdhani:wght@600;700;800&display=swap"
        ),
        FontPairOption(
            id = "outfit_jakarta",
            headingFont = "Outfit",
            bodyFont = "Plus Jakarta Sans",
            googleFontLink = "https://fonts.googleapis.com/css2?family=Outfit:wght@600;700;800&family=Plus+Jakarta+Sans:wght@400;500;600;700&display=swap"
        ),
        FontPairOption(
            id = "space_dmsans",
            headingFont = "Space Grotesk",
            bodyFont = "DM Sans",
            googleFontLink = "https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;700&family=Space+Grotesk:wght@600;700&display=swap"
        ),
        FontPairOption(
            id = "montserrat_roboto",
            headingFont = "Montserrat",
            bodyFont = "Roboto",
            googleFontLink = "https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;800&family=Roboto:wght@400;500;700&display=swap"
        )
    )

    val ARCHITECTURES = listOf(
        "Admin + User Panel (2 Single HTML Files + Firebase RTDB)",
        "User Only (1 Single HTML File, Pure LocalStorage)",
        "Online Multiplayer Game (1 Single HTML File + Firebase RTDB)"
    )

    val TARGET_PLATFORMS = listOf(
        "HopWeb Mobile IDE",
        "Sketchware Pro WebView",
        "InfinityFree / cPanel Hosting",
        "GitHub Pages / Netlify",
        "Standard Android Studio WebView"
    )
}
