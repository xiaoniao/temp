package com.kunyao.assistant.web.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.entity.PageData;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.enums.CrossEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.core.model.CrossInfo;
import com.kunyao.assistant.core.model.CrossTimes;
import com.kunyao.assistant.core.model.Province;
import com.kunyao.assistant.core.model.Role;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.core.model.UserRole;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.PluploadUtil;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.CollectionMapper;
import com.kunyao.assistant.web.dao.CrossInfoMapper;
import com.kunyao.assistant.web.dao.CrossTimesMapper;
import com.kunyao.assistant.web.dao.UserMapper;
import com.kunyao.assistant.web.service.CityService;
import com.kunyao.assistant.web.service.CrossInfoService;
import com.kunyao.assistant.web.service.OrderService;
import com.kunyao.assistant.web.service.ProvinceService;
import com.kunyao.assistant.web.service.RoleService;
import com.kunyao.assistant.web.service.UserRoleService;
import com.kunyao.assistant.web.service.UserService;

@Service
public class CrossInfoServiceImpl extends GenericServiceImpl<CrossInfo, Integer> implements CrossInfoService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(CrossInfoServiceImpl.class);
	
	@Resource
	private CrossInfoMapper crossInfoMapper;
	
	@Resource
	private CrossTimesMapper timeMapper;
	
	@Resource
	private CollectionMapper collectionMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private CityService cityService;
	
	@Resource
	private ProvinceService provinceService;
	
	@Resource
	private MySQLMaxValueIncrementer crossNumberIncrementer;
	
	@Resource
	private CrossTimesMapper crossTimesMapper;

	@Resource
	private OrderService orderService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private UserRoleService userRoleService;
	
	@Resource
	private RoleService roleService;
	
	public GenericDao<CrossInfo, Integer> getDao() {
		return crossInfoMapper;
	}
	
	@Override
	public CrossInfo login(String username, String password) throws ServiceException {
		if (StringUtils.isNull(username) || StringUtils.isNull(password)) {
			throw new ServiceException(ResultCode.ABNORMAL_REQUEST);
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username, StringUtils.getMD5(Constant.USER_PASSWORD_SECRET + password));
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.getSession().setTimeout(1000 * 60 * 30); // 30分钟有效期
			currentUser.login(token);
        } catch (UnknownAccountException uae) {
        	// 未知账户
        	throw new ServiceException(ResultCode.LOGIN_ERROR);
        } catch (IncorrectCredentialsException ice) {
        	// 错误的凭证
        	throw new ServiceException(ResultCode.LOGIN_ERROR);
        } catch (LockedAccountException lae) {
        	// 账户已锁定
        	throw new ServiceException(ResultCode.LOGIN_ERROR);
        } catch (ExcessiveAttemptsException eae) {
        	// 错误次数过多
        	throw new ServiceException(ResultCode.LOGIN_ERROR);
        } catch (AuthenticationException ae) {
        	// 用户名或密码不正确
        	throw new ServiceException(ResultCode.LOGIN_ERROR);
        }
		if (!currentUser.isAuthenticated()) {
			throw new ServiceException(ResultCode.LOGIN_ERROR);
		}
		CrossInfo crossInfo = crossInfoMapper.findByUsername(username);
		if (crossInfo == null) {
			throw new ServiceException(ResultCode.LOGIN_ERROR);
		}
		return crossInfo;
	}

	@Override
	public PageData<CrossInfo> queryCrossList(String startDate, String endDate, Integer cityId, String search, Integer currentPage, Integer pagesize) {
		if (StringUtils.isNull(search)) { search = "";}
		search = "%" + search + "%";
		
		PageData<CrossInfo> pageData = new PageData<>();
		Integer timeStatusIdel = CrossEnum.BookStatus.IDLE.getValue();
		Integer timeStatusISub = CrossEnum.BookStatus.SUBSCRIBE.getValue();
		Integer startpos = null;
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
		
		List<CrossInfo> crossInfos = crossInfoMapper.findCrossList(startDate, endDate, cityId, search, timeStatusIdel, timeStatusISub, startpos, pagesize);
		pageData.setList(crossInfos);
		pageData.setAllRow(crossInfoMapper.findCrossListCount(startDate, endDate, cityId, search, timeStatusIdel, startpos, pagesize));
		pageData.setTotalPage(PageData.countTotalPage(pagesize, pageData.getAllRow()));
		pageData.setCurrentPage(currentPage++);
		pageData.setPageSize(pagesize);
		return pageData;
	}

	@Override
	public CrossInfo queryCrossInfo(Integer crossUserId, String startDate, String endDate) {
		CrossInfo crossInfo = null;
		if (startDate != null && endDate != null)
			crossInfo = crossInfoMapper.findCrossDetail(crossUserId, startDate, endDate);
		else 
			crossInfo = crossInfoMapper.findCrossInfo(crossUserId);
		return crossInfo;
	}

	@Override
	public CrossInfo querySimpleCrossInfo(Integer crossInfoId) {
		return crossInfoMapper.findSimpleCrossInfo(crossInfoId);
	}
	
	/**
	 * invalid 不可预约
	 * valid 可预约
	 * selected 已经被预约
	 */
	@Override
	public List<List<Map<String, String>>> queryCrossTimeList(Integer userId) {
		List<Map<String, String>> list = createTimeList();
		String startDate = list.get(0).get("date") + " 00:00:00";
		String endDate = list.get(list.size() - 1).get("date") + " 00:00:00";
		List<CrossTimes> crossTimes = timeMapper.findCrossTimeByDate(userId, startDate, endDate);
		if (crossTimes != null) {
			for (Map<String, String> map : list) {
				String date = map.get("date");
				for (CrossTimes time : crossTimes) {
					if (time.getStatus() != null && (time.getStatus().intValue() == CrossEnum.BookStatus.SUBSCRIBE.getValue() 
							|| time.getStatus().intValue() == CrossEnum.BookStatus.CLOSE.getValue()) 
							&& date.equals(DateUtils.parseYMDTime(time.getTime()))) {
						map.put("status", "selected");
						break;
					}
				}
			}
		}
		
		List<List<Map<String, String>>> result = new ArrayList<>();
		for (int i = 1; i <= list.size(); i++) {
			if (i % 7 == 0) {
				result.add(list.subList(i-7, i));
			}
		}
		return result;
	}
	
	/** 生成未来30天日期 **/
	private List<Map<String, String>> createTimeList() {
		List<Map<String, String>> result = new ArrayList<>();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		
		int indexWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
		for (int i = indexWeek; i > 0; i--) {
			Date preDate = new Date(date.getTime() - DateUtils.DAY * i);
			Map<String, String> map = new HashMap<>();
			map.put("status", "invalid");
			map.put("date", simpleDateFormat.format(preDate));
			result.add(map);
		}

		for (int i = 0; i < 30; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("status", "valid");
			map.put("date", simpleDateFormat.format(date));
			if (i == 0 && date.getTime() > DateUtils.parseFullDate(DateUtils.parseYMDTime(date) + " 19:00:00").getTime())
				map.put("status", "invalid");
			result.add(map);
			date = new Date(date.getTime() + DateUtils.DAY);
		}

		if (result.size() % 7 > 0) {
			int reduce = 7 - result.size() % 7;
			for (int i = 0; i < reduce; i++) {
				Map<String, String> map = new HashMap<>();
				map.put("status", "invalid");
				map.put("date", simpleDateFormat.format(date));
				result.add(map);
				date = new Date(date.getTime() + DateUtils.DAY);
			}
		}
		return result;
	}

	/**
	 * 是否可以被预约
	 * @throws ServiceException 
	 */
	@Override
	public boolean canBook(Integer crossUserId, String startDate, String endDate) throws ParseException, ServiceException {
		// 检查金鹰是否离职
		User userModel = new User();
		userModel.setId(crossUserId);
		User authentication = userService.findOne(userModel);
		if (authentication == null || authentication.getStatus().intValue() == BaseEnum.Status.BASE_STATUS_DISABLED.getValue()) {
			return false;
		}
		// 检查是否可以预约
		int day = DateUtils.getBetweenDay(startDate, endDate);
		startDate += " 00:00:00";
		endDate += " 00:00:00";
		List<CrossTimes> crossTimes = timeMapper.findCrossTimeByStatus(crossUserId, CrossEnum.BookStatus.IDLE.getValue(), startDate, endDate);
		if (crossTimes != null && crossTimes.size() == day) {
			return true;
		}
		return false;
	}
	
	@Override
	public int createCrossInfo(CrossInfo crossinfo) throws ServiceException{
		
		User crossUser = new User();
		crossUser.setMobile(crossinfo.getMobile());
		
		try {
			List<User> userList = userService.findList(null, null, crossUser);
			if (userList != null && userList.size() > 0){
				throw new ServiceException(ResultCode.MOBILE_PHONE_ALREADY_EXISTS);
			}
		} catch (Exception e) {
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
		}
		
		Integer crossNumber = crossNumberIncrementer.nextIntValue();
		
		User user = new User();
		user.setMobile(crossinfo.getMobile());
		user.setCreateTime(new Date());
		user.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		user.setUsername(crossinfo.getMobile());
		String afterSecretPassword = StringUtils.getMD5(Constant.USER_PASSWORD_SECRET + crossinfo.getEncryptionPassword());
		user.setPassword(afterSecretPassword);
		user.setType("cross");
		try {
			userMapper.insert(user);
		} catch (Exception e) {
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
		}
		
		try {
			City city = cityService.findByID(crossinfo.getNativePlaceCityId());
			Province province = provinceService.findByID(crossinfo.getNativePlaceProvinceId());
			String nativePlace = province.getName() + "-" + city.getName();
			String cityName =  StringUtils.getFirstSpell(city.getName()).toUpperCase();
			crossinfo.setNativePlace(nativePlace);
			crossinfo.setMobile(null);
			crossinfo.setUserId(user.getId());
			crossinfo.setCrossNumber(cityName + "_" + crossNumber.toString());
			crossInfoMapper.insert(crossinfo);
		} catch (Exception e) {
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
		}
		
		User userModel = new User();
		userModel.setId(user.getId());
		userModel.setUserinfoId(crossinfo.getId());
		
		try {
			userMapper.updateByID(userModel);
		} catch (Exception e) {
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
		}
		
		Date todayDate = DateUtils.parseYMDDate(DateUtils.parseYMDTime(new Date()));
		long todayTime = todayDate.getTime();
		Date yearLastDate = DateUtils.getCurrYearLast();
		long yearLastTime = yearLastDate.getTime();
		
		for (long i = todayTime; i <= yearLastTime; i = i + 1000 * 60 * 60 * 24) {
			Date d = new Date(i);
			CrossTimes model = new CrossTimes();
			model.setStatus(CrossEnum.BookStatus.IDLE.getValue());
			model.setTime(d);
			model.setUserId(user.getId());
			
			try {
				crossTimesMapper.insert(model);
			} catch (Exception e) {
				throw new ServiceException(ResultCode.EXCEPTION_ERROR);
			}
		}
		
		try {
			Role rModel = new Role();
			rModel.setSign("cross");
			Role role = roleService.findOne(rModel);
			
			UserRole userRole = new UserRole();
			userRole.setRoleId(role.getId());
			userRole.setUserId(user.getId());
			userRoleService.insert(userRole);               
		} catch (Exception e) {
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
		}
		
		return 1;
	}

	@Override
	public List<CrossInfo> selectCrossInfoList(Integer currentPage, Integer pagesize, CrossInfo crossInfo) {
		
		Integer startpos = null;
		
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
		
		List<CrossInfo> crossInfoList = crossInfoMapper.selectListByCondition(startpos, pagesize, crossInfo);
		
		for (CrossInfo cross : crossInfoList) {
			String[] banners = cross.getBanners().split(";");
			cross.setBanners(banners[0]);
		}
		
		return crossInfoList;
	}

	@Override
	public int deleteBanners(Integer crossId, String banners) {
			String images = banners.substring(0, banners.length() - 1);
			CrossInfo crossIn = crossInfoMapper.findCrossById(crossId);
			String[] selectPictrues = crossIn.getBanners().split(";"); 					    // 数据库里的图片
			String[] inputPictrues = images.split(";"); 									// 前端传入的
			String[] arrResult = PluploadUtil.arrContrast(selectPictrues, inputPictrues);   // 去掉重复值
			String pictrueList = "";
			for (int i = 0; i < arrResult.length; i++) {
				if (i == arrResult.length - 1) {
					pictrueList += arrResult[i];
					break;
				}
				pictrueList += arrResult[i] + ";";
			}

			try {
				CrossInfo deletePictrue = new CrossInfo();
				deletePictrue.setId(crossIn.getId());
				deletePictrue.setBanners(pictrueList);
				crossInfoMapper.updateByID(deletePictrue);
			} catch (Exception e) {
				return -1;
			}
			return 1;
	}

	@Override
	public CrossInfo findCrossById(Integer crossId) {
		return crossInfoMapper.findCrossById(crossId);
	}

	@Override
	public int updateCrossInfo(CrossInfo crossInfo) throws ServiceException{
		
		User user = new User();
		user.setId(crossInfo.getUserId());
		user.setStatus(crossInfo.getStatus());
		if (!crossInfo.getStatus().equals(BaseEnum.Status.BASE_STATUS_ENABLE.getValue())){
			//判断需检验该金鹰是否有正在服务的订单，有服务中的订单提示“该金鹰正在服务，请确认服务完成后离职”
			int crossInfoOrderStatus = orderService.findOrderByCrossUserId(crossInfo.getUserId());
			
			if (crossInfoOrderStatus > 0){
				throw new ServiceException(ResultCode.CROSS_UPDATE_STATUS);
			}
			
			try {
				userMapper.updateByID(user);
			} catch (Exception e) {
				throw new ServiceException(ResultCode.EXCEPTION_ERROR);
			}
			
		} else {
			
			try {
				userMapper.updateByID(user);
			} catch (Exception e) {
				throw new ServiceException(ResultCode.EXCEPTION_ERROR);
			}
		}
		
		if (crossInfo.getBanners() != null && !StringUtils.isNull(crossInfo.getBanners())) {
			if (StringUtils.isNull(crossInfo.getBanners())) {
				throw new ServiceException(ResultCode.LOAD_PICTURE_ERROR);
			}
			
			CrossInfo selectCrossInfo = crossInfoMapper.findCrossById(crossInfo.getId());
			String selectPictrue = selectCrossInfo.getBanners();
			String linkPictrue = selectPictrue + ";" + crossInfo.getBanners();
			String imagePath = linkPictrue.replaceAll("\"", "");
			crossInfo.setBanners(imagePath);
		}
		
		try {
			crossInfoMapper.updateCrossById(crossInfo);
		} catch (Exception e) {
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
		}

		return 1;
	}
	@Override
	public int findUserByCityIdCount(Integer cityId) {
		return crossInfoMapper.findUserByCityIdCount(cityId);
	}

	@Override
	public List<CrossInfo> findUserByUserId(Integer status, String stayDate, String endDate) {
		return crossInfoMapper.findUserByUserId(status, stayDate, endDate);
	}

	@Override
	public void updatePassword(Integer crossUserId, String oldPassword, String newPassword) throws ServiceException {
		User user = userService.findByID(crossUserId);
		if (user == null) {
			throw new ServiceException(ResultCode.ABNORMAL_REQUEST);
		}
		
		if (!user.getPassword().equals(StringUtils.getMD5(Constant.USER_PASSWORD_SECRET + oldPassword))) {
			throw new ServiceException(ResultCode.PASSWORD_WRONG);
		}
		user.setPassword(StringUtils.getMD5(Constant.USER_PASSWORD_SECRET + newPassword));
		userService.update(user);
	}

	@Override
	public void updateContactPhone(Integer crossUserId, String phone) throws ServiceException {
		CrossInfo crossInfo = crossInfoMapper.findByUserId(crossUserId);
		crossInfo.setContactPhone(phone);
		update(crossInfo);
	}

	@Override
	public void uploadGetuiId(Integer crossUserId, String getuiId) throws ServiceException {
		CrossInfo crossInfo = crossInfoMapper.findCrossInfo(crossUserId);
		crossInfo.setGetuiId(getuiId);
		update(crossInfo);
	}

	@Override
	public void uploadLocation(Integer crossUserId, String lat, String lng) throws ServiceException {
		if (StringUtils.isNull(lat) || StringUtils.isNull(lng)) {
			return;
		}
		CrossInfo crossInfo = crossInfoMapper.findCrossInfo(crossUserId);
		crossInfo.setLat(lat);
		crossInfo.setLng(lng);
		update(crossInfo);
	}
	
	@Override
	public CrossInfo findByOrderId(Integer orderId) {
		return crossInfoMapper.findByOrderId(orderId);
	}
}
