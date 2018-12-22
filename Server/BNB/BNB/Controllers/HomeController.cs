using BNB.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity.Core.Objects;
using System.Globalization;
using System.Linq;
using System.Threading;
using System.Web;
using System.Web.Http;
using System.Web.Http.Cors;
using System.Web.Http.Description;
using System.Web.Mvc;
using System.Data.Entity;
using Newtonsoft.Json;

namespace BNB.Controllers
{

    /// <summary>
    /// Home controller.
    /// </summary>
    [System.Web.Http.AllowAnonymous]
    [ApiExplorerSettings(IgnoreApi = false)]
    public class HomeController : Controller
    {

        BNBEntityDataModel BNBDB = new BNBEntityDataModel();

        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Feedback()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }

        public ActionResult Instruction()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }

        /// <summary>
        /// Get list of existing map markers
        /// </summary>
        /// <returns>Return jsn model with all sensors</returns>
        /// <seealso cref="AddSensorInfo(BNB.Sensor)">Add new sensor</seealso>
        /// <seealso cref="AddUserFeedback(BNB.Feedback)">Add feedback</seealso>
        [System.Web.Http.Route("getallmarkers")]
        [System.Web.Http.HttpGet]
        [System.Web.Http.ActionName("Get all Records")]
        public IEnumerable<Sensor> GetMapMarker()
        {
            BNBDB.Configuration.ProxyCreationEnabled = false; 
            var result = BNBDB.Sensor.Include(e =>e.Location).Include(e=>e.Author).ToList();
            return result;
        }

        /// <summary>
        /// Insert new sensor info
        /// </summary>
        /// <returns>Return GUID Key for sensor</returns>
        /// <seealso cref="GetMapMarker()">Get all markers</seealso>
        /// <seealso cref="AddUserFeedback(BNB.Feedback)">Add feedback</seealso>
        [System.Web.Http.Route("addnewsesor")]
        [System.Web.Http.HttpGet]
        [System.Web.Http.ActionName("Add new sensor")]
        public JsonResult AddSensorInfo([FromBody]Sensor sensor)
        {
            //Guid SKey = Guid.NewGuid();
            //sensor.SKey = SKey.ToString();

            try
            {
                Sensor sensorInDB = GetSensorByKey(sensor.SKey);
                if (sensorInDB == null)
                {
                    BNBDB.Sensor.Add(sensor);
                    BNBDB.Location.Add(sensor.Location);
                    BNBDB.Author.Add(sensor.Author);
                  
                }
                else {
                    sensorInDB.Author.AName = sensor.Author.AName;
                    sensorInDB.Author.APhone = sensor.Author.APhone;
                    sensorInDB.Author.AEmail = sensor.Author.AEmail;

                    sensorInDB.Location.LCity = sensor.Location.LCity;
                    sensorInDB.Location.LCountry = sensor.Location.LCountry;
                    sensorInDB.Location.LCoordFromMap = sensor.Location.LCoordFromMap;
                    sensorInDB.Location.LFromMap = sensor.Location.LFromMap;

                    sensorInDB.SName = sensor.SName;
                    sensorInDB.SDetails = sensor.SDetails;
                    sensorInDB.SText = sensor.SText;
                    sensorInDB.SLastChangeDate = DateTime.Now;
                }

                BNBDB.SaveChanges();
                return Json(new { success = sensor.SKey }, JsonRequestBehavior.AllowGet);
            }
            catch (Exception e)
            {
                return Json(new { error = e.Message }, JsonRequestBehavior.AllowGet);
            }
           
        }

        /// <summary>
        /// Insert new user feedback
        /// </summary>
        /// <returns>Return true if received</returns>
        /// <seealso cref="GetMapMarker()">All archived data</seealso>
        /// <seealso cref="AddSensorInfo(Sensor)">Add new sensor</seealso>
        [System.Web.Http.Route("addfeedback")]
        [System.Web.Http.HttpPost]
        [System.Web.Http.ActionName("Add new feedback")]
        public JsonResult AddUserFeedback([FromBody]Feedback data)
        {
            try
            {
                BNBDB.Feedback.Add(data);
                BNBDB.SaveChanges();


                return Json(new { success = "Feedback received" }, JsonRequestBehavior.AllowGet);
            }
            catch (Exception e)
            {
                return Json(new { error = e.Message }, JsonRequestBehavior.AllowGet);
            }

        }

        /// <summary>
        /// Get sensors by keyword
        /// </summary>
        /// <returns>Return sensors</returns>
        /// <seealso cref="GetMarkers()">Get map markers</seealso>
        /// <seealso cref="addSensor(Sensor)">Add new sensor</seealso>//////////////////////
        [System.Web.Http.Route("getByKeyword")]
        [System.Web.Http.HttpGet]
        [System.Web.Http.ActionName("Get sensors by keyword")]
        public IEnumerable<Sensor> GetMarkersByKeyWord([FromBody]String data)
        {
            BNBDB.Configuration.ProxyCreationEnabled = false; //added line
            var result = BNBDB.Sensor.Include(e => e.Location).Include(e => e.Author).
                Where(s=>s.Location.LCountry.Contains(data) ||
                        s.Location.LCity.Contains(data) ||
                        s.Location.LFromMap.Contains(data)).ToList();
            return result;
        }

        /// <summary>
        /// Get sensor by key
        /// </summary>
        /// <returns>Return sensor</returns>
        /// <seealso cref="GetMarkers()">Get map markers</seealso>
        /// <seealso cref="addSensor(Sensor)">Add new sensor</seealso>//////////////////////
        [System.Web.Http.Route("getSensorByKey")]
        [System.Web.Http.HttpGet]
        [System.Web.Http.ActionName("Get sensor by key")]
        public Sensor GetSensorByKey(String sensorKey)
        {
            BNBDB.Configuration.ProxyCreationEnabled = false; 
            var result = BNBDB.Sensor.Include(e => e.Location).Include(e => e.Author).
                Where(s => s.SKey == sensorKey).SingleOrDefault();
            return result;
        }

        public ActionResult Change(String LanguageAbbrevation)
        {
            if (LanguageAbbrevation != null)
            {
                Thread.CurrentThread.CurrentCulture = CultureInfo.CreateSpecificCulture(LanguageAbbrevation);
                Thread.CurrentThread.CurrentUICulture = new CultureInfo(LanguageAbbrevation);
            }

            HttpCookie cookie = new HttpCookie("Language");
            cookie.Value = LanguageAbbrevation;
            Response.Cookies.Add(cookie);

            return View("Index");
        }



    }
}